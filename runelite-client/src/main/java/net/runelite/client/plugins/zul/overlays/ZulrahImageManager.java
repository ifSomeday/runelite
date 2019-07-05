package net.runelite.client.plugins.zul.overlays;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Prayer;
import net.runelite.client.plugins.zul.ZulrahPlugin;
import net.runelite.client.plugins.zul.phase.ZulrahType;

@Slf4j
public class ZulrahImageManager {
    
    private static final BufferedImage[] zulrahBufferedImages = new BufferedImage[3];
    private static final BufferedImage[] smallZulrahBufferedImages = new BufferedImage[3];
    private static final BufferedImage[] prayerBufferedImages = new BufferedImage[2];

    public static BufferedImage getZulrahBufferedImage(ZulrahType type) {

        if(type == ZulrahType.RANGE) {

            if(zulrahBufferedImages[0] == null) {
                zulrahBufferedImages[0] = getBufferedImage("assets/zulrah_range.png");
            }
            return(zulrahBufferedImages[0]);

        } else if(type == ZulrahType.MAGIC) {

            if(zulrahBufferedImages[1] == null) {
                zulrahBufferedImages[1] = getBufferedImage("assets/zulrah_magic.png");
            }
            return(zulrahBufferedImages[1]);

        } else if(type == ZulrahType.MELEE) {

            if(zulrahBufferedImages[2] == null) {
                zulrahBufferedImages[2] = getBufferedImage("assets/zulrah_melee.png");
            }
            return(zulrahBufferedImages[2]);

        }

        return(null);

    }

    public static BufferedImage getSmallZulrahBufferedImage(ZulrahType type) {

        if(type == ZulrahType.RANGE) {

            if(smallZulrahBufferedImages[0] == null) {
                smallZulrahBufferedImages[0] = getBufferedImage("assets/zulrah_range_sm.png");
            }
            return(smallZulrahBufferedImages[0]);

        } else if(type == ZulrahType.MAGIC) {

            if(smallZulrahBufferedImages[1] == null) {
                smallZulrahBufferedImages[1] = getBufferedImage("assets/zulrah_magic_sm.png");
            }
            return(smallZulrahBufferedImages[1]);

        } else if(type == ZulrahType.MELEE) {

            if(smallZulrahBufferedImages[2] == null) {
                smallZulrahBufferedImages[2] = getBufferedImage("assets/zulrah_melee_sm.png");
            }
            return(smallZulrahBufferedImages[2]);

        }

        return(null);

    }

    public static BufferedImage getProtectionPrayerBufferedImage(Prayer prayer) {

        if(prayer == Prayer.PROTECT_FROM_MAGIC) {

            if(prayerBufferedImages[0] == null) {
                prayerBufferedImages[0] = getBufferedImage("/prayers/protect_from_magic.png");
            }
            return(prayerBufferedImages[0]);

        } else if(prayer == Prayer.PROTECT_FROM_MISSILES) {

            if(prayerBufferedImages[1] == null) {
                prayerBufferedImages[1] = getBufferedImage("/prayers/protect_from_missiles.png");
            }
            return(prayerBufferedImages[1]);

        }

        return(null);

    }


    private static BufferedImage getBufferedImage(String path) {

        BufferedImage image = null;

        try {

            InputStream in = ZulrahPlugin.class.getResourceAsStream(path);
            image = ImageIO.read(in);

        } catch (IOException e) {

            log.warn("Error loading image {}", e);

        }

        return(image);

    }

}