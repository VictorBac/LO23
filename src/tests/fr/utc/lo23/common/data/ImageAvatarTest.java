package fr.utc.lo23.common.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mar on 13/12/2015.
 */
public class ImageAvatarTest {

    @Test
    public void testSetPath() throws Exception {
        ImageAvatar aGoodImage = new ImageAvatar("C:/Users/Mar/Desktop/LO23/Penguins.jpg");
        System.out.println("1"+aGoodImage.getPath());

    }

    @Test
    public void testGetImageAvatar() throws Exception {

    }
}