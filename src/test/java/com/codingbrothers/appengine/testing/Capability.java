package com.codingbrothers.appengine.testing;

public enum Capability {

	BLOBSTORE(com.google.appengine.api.capabilities.Capability.BLOBSTORE),
	DATASTORE(com.google.appengine.api.capabilities.Capability.DATASTORE),
	DATASTORE_WRITE(com.google.appengine.api.capabilities.Capability.DATASTORE_WRITE),
	IMAGES(com.google.appengine.api.capabilities.Capability.IMAGES),
	MAIL(com.google.appengine.api.capabilities.Capability.MAIL),
	MEMCACHE(com.google.appengine.api.capabilities.Capability.MEMCACHE),
	PROSPECTIVE_SEARCH(com.google.appengine.api.capabilities.Capability.PROSPECTIVE_SEARCH),
	TASKQUEUE(com.google.appengine.api.capabilities.Capability.TASKQUEUE),
	URL_FETCH(com.google.appengine.api.capabilities.Capability.URL_FETCH),
	XMPP(com.google.appengine.api.capabilities.Capability.XMPP);

	private final com.google.appengine.api.capabilities.Capability capability;

	private Capability(com.google.appengine.api.capabilities.Capability capability) {
		this.capability = capability;

	}

	com.google.appengine.api.capabilities.Capability getCapability() {
		return capability;
	}
}
