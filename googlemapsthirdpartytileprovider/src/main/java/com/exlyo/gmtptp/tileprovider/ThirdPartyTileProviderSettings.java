package com.exlyo.gmtptp.tileprovider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ThirdPartyTileProviderSettings {
	@Nullable
	private String bingApiKey = null;
	@Nullable
	private String ignApiKey = null;
	@Nullable
	private String ignUserAgentString;
	@NonNull
	private String language = "en-US";
	@NonNull
	private List<File> offlineMapFileList = new ArrayList<>();

	public ThirdPartyTileProviderSettings setBingApiKey(@Nullable final String _bingApiKey) {
		bingApiKey = _bingApiKey;
		return this;
	}

	public ThirdPartyTileProviderSettings setIgnApiKey(@Nullable final String _ignApiKey) {
		ignApiKey = _ignApiKey;
		return this;
	}

	public ThirdPartyTileProviderSettings setIgnUserAgentString(@Nullable final String _ignUserAgentString) {
		ignUserAgentString = _ignUserAgentString;
		return this;
	}

	public ThirdPartyTileProviderSettings setLanguage(@NonNull final String _language) {
		language = _language;
		return this;
	}

	@Nullable
	public String getBingApiKey() {
		return bingApiKey;
	}

	@Nullable
	public String getIgnApiKey() {
		return ignApiKey;
	}

	public String getIgnUserAgentString() {
		return ignUserAgentString;
	}

	@NonNull
	public String getLanguage() {
		return language;
	}

	@NonNull
	public List<File> getOfflineMapFileList() {
		return offlineMapFileList;
	}

	public ThirdPartyTileProviderSettings setOfflineMapFileList(@NonNull final List<File> _offlineMapFileList) {
		offlineMapFileList = _offlineMapFileList;
		return this;
	}
}
