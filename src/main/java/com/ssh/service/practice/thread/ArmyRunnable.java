package com.ssh.service.practice.thread;

public class ArmyRunnable implements Runnable{

	volatile boolean keepRunning;

	@Override
	public void run() {
		while (keepRunning) {

		}
	}
}
