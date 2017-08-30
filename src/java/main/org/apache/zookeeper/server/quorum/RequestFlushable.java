package org.apache.zookeeper.server.quorum;

import org.apache.zookeeper.server.Request;

public interface RequestFlushable {
	
	public void flush(Request request);

}
