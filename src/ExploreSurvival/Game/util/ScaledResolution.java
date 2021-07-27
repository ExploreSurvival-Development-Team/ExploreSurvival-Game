package exploresurvival.game.util;

import exploresurvival.game.ExploreSurvival;

public class ScaledResolution
{
	public ScaledResolution() {
		this(ExploreSurvival.getInstance().gamesettings,ExploreSurvival.getInstance().width,ExploreSurvival.getInstance().height);
	}
    public ScaledResolution(GameSettings settings, int i, int j)
    {
        scaledWidth = i;
        scaledHeight = j;
        scaleFactor = 1;
        int k = settings.guiScale;
        if(k == 0)
        {
            k = 1000;
        }
        for(; scaleFactor < k && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240; scaleFactor++) { }
        field_25121_a = (double)scaledWidth / (double)scaleFactor;
        field_25120_b = (double)scaledHeight / (double)scaleFactor;
        scaledWidth = (int)Math.ceil(field_25121_a);
        scaledHeight = (int)Math.ceil(field_25120_b);
    }

    public int getScaledWidth()
    {
        return scaledWidth;
    }

    public int getScaledHeight()
    {
        return scaledHeight;
    }

    private int scaledWidth;
    private int scaledHeight;
    public double field_25121_a;
    public double field_25120_b;
    public int scaleFactor;
}
