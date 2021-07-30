package exploresurvival.game.util;

import java.util.UUID;

public class Session {
	public final String username;
	public final String session;
	public final UUID uuid;
	public final boolean isOffline;
	public Session(String username, String session, UUID uuid, boolean isOffline) {
		this.username = username;
		this.session = session;
		this.uuid = uuid;
		this.isOffline = isOffline;
	}
	public int hashCode() {
		return username.hashCode()*session.hashCode()*uuid.hashCode();
	}
}
