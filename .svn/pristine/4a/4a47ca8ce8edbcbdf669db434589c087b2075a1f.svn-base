package com.tiamaes.bike.config;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;

@Component
public class ChannelRepository {
	/**
	 * key: channel.hashCode() value: channel
	 */
	private HashMap<String, Channel> channelCache = new HashMap<String, Channel>();
	/**
	 * key: 【桩】ID value: channel
	 */
	private HashMap<String, Channel> stationsCache = new HashMap<String, Channel>();
	/**
	 * key: 【锁】ID value: channel
	 */
	private HashMap<String, Channel> locksCache = new HashMap<String, Channel>();
	
	public ChannelRepository put(String key, Channel value) {
		channelCache.put(key, value);
		return this;
	}

	public Channel get(String key) {
		return channelCache.get(key);
	}

	public void remove(String key) {
		this.channelCache.remove(key);
	}

	public int size() {
		return this.channelCache.size();
	}
	
	
	public ChannelRepository putStation(String key, Channel value) {
		stationsCache.put(key, value);
		return this;
	}

	public Channel getStation(String key) {
		return stationsCache.get(key);
	}

	public void removeStation(String key) {
		this.stationsCache.remove(key);
	}

	public int sizeStation() {
		return this.stationsCache.size();
	}
	
	
	public ChannelRepository putLock(String key, Channel value) {
		locksCache.put(key, value);
		return this;
	}

	public Channel getLock(String key) {
		return locksCache.get(key);
	}

	public void removeLock(String key) {
		this.locksCache.remove(key);
	}

	public int sizeLock() {
		return this.locksCache.size();
	}
}
