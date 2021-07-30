package exploresurvival.game.util;

import java.util.UUID;

import com.google.gson.Gson;

public class Session {
	public class VerityResponse {
		public boolean success;
	}
	public final String username;
	public final String session;
	public final UUID uuid;
	public final boolean isOffline;
	public Session(String username, String session, UUID uuid, boolean isOffline) {
		this.username = username;
		this.session = session;
		this.uuid = uuid;
		if(!isOffline) {
			Gson gson=new Gson();
			try {
				VerityResponse r=gson.fromJson(HttpUtil.sendGet(String.format("https://www.opencomputers.ml:7331/ExploreSurvival/veritySession.jsp?username=%s&session=%s&uuid=%s", username, session, uuid.toString())), VerityResponse.class);
				isOffline=!r.success;
			} catch(Exception e) {
				isOffline=true;
				e.printStackTrace();
			}
		}
		this.isOffline = isOffline;
	}
	public int hashCode() {
		return username.hashCode()*session.hashCode()*uuid.hashCode();
	}
}
