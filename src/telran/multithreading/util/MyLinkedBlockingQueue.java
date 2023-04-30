package telran.multithreading.util;

import java.nio.channels.IllegalSelectorException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.function.*;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {

	private static final int DEFAULT_LIMIT = Integer.MAX_VALUE;
	LinkedList<E> list = new LinkedList<>();
	int limit;
	ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	Lock readLock = rwlock.readLock();
	Lock writeLock = rwlock.writeLock();
	Condition waitingConsumer = writeLock.newCondition();
	Condition waitingProducer = writeLock.newCondition();

	private <F> F wrapLock(Lock lock, Supplier<F> supplier) {
		lock.lock();
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	public MyLinkedBlockingQueue() {
		this(DEFAULT_LIMIT);
	}

	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public E remove() {
		E res = poll();
		if (res == null) {
			throw new NoSuchElementException();
		}
		return res;
	}

	@Override
	public E poll() {
		return wrapLock(writeLock, () -> {
			if (size() == 0) {
				return null;
			}
			E res = list.remove();
			if (res != null) waitingProducer.signal();
			return res;
		});

	}

	@Override
	public E element() {
		E res = peek();
		if (res == null) {
			throw new NoSuchElementException();
		}
		return res;

	}

	@Override
	public E peek() {
		return wrapLock(readLock, () -> {
			if (size() == 0) {
				return null;
			}
			return list.peek();
		});
	}

	@Override
	public int size() {
		return wrapLock(readLock, () -> list.size());
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return wrapLock(readLock, () -> list.iterator());
	}

	@Override
	public Object[] toArray() {
		return wrapLock(readLock, () -> list.toArray());
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return wrapLock(readLock, () -> list.toArray(a));
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return wrapLock(readLock, () -> list.containsAll(c));
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return wrapLock(writeLock, () -> c.stream().map(e -> add(e)).anyMatch(b -> !b) ? false : true);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return wrapLock(writeLock, () -> list.removeAll(c));
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return wrapLock(writeLock, () -> {
			list.stream().forEach(e -> {
				if (!c.contains(e)) {
					remove(e);
				}
			});
			return true;
		});
	}

	@Override
	public void clear() {
		wrapLock(writeLock, () -> {list.clear(); return true;});
	}

	@Override
	public boolean add(E e) {
		boolean res = offer(e);
		if (!res) {
			throw new IllegalSelectorException();
		}
		return res;
	}

	@Override
	public boolean offer(E e) {
		return wrapLock(writeLock, () -> {
			if (limit - list.size() <= 0) {
				return false;
			}
			boolean res = list.add(e);
			if (res) waitingConsumer.signal();
			return res;
		});
	}

	@Override
	public void put(E e) throws InterruptedException {
		offer(e, Long.MAX_VALUE, TimeUnit.DAYS);
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			boolean res = false;
			while (list.size() != 0) {
				waitingProducer.await(timeout, unit);
			}
			if (list.size() == 0) {
				res = list.add(e);
				if (res) waitingConsumer.signal();
				res = true;
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		return poll(Long.MAX_VALUE, TimeUnit.DAYS);
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			while (list.size() == 0) {
				waitingConsumer.await(timeout, unit);
			}
			E res = list.remove();
			if (res != null) waitingProducer.signal();
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		return limit - size();
	}

	@Override
	public boolean remove(Object o) {
		return wrapLock(writeLock, () -> {
			boolean res = list.remove(o);
			if (res) {
				waitingProducer.signal();
			}
			return res;
		});
	}

	@Override
	public boolean contains(Object o) {
		return wrapLock(writeLock, () -> list.contains(o));
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

}
