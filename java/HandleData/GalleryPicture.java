/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HandleData;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author doklea
 */
public class GalleryPicture {
    private UUID pictureID;
    private String userName;
    private String tag;
    private byte[] picBuffer;
    private Integer permissions;

    public GalleryPicture(UUID PictureID, String userName, String Tag, File Picture, Integer Permissions){
        this.pictureID = PictureID;
        this.userName = userName;
        this.tag = Tag;
        this.permissions = Permissions;
        DataInputStream in = null;
        try {
            picBuffer = new byte[(int)Picture.length()];
            in = new DataInputStream(new FileInputStream(Picture));
            in.read(picBuffer);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GalleryPicture(String userName, String Tag, File Picture, Integer Permissions){
        this.pictureID = UUID.randomUUID();
        this.userName = userName;
        this.tag = Tag;
        this.permissions = Permissions;
        DataInputStream in = null;
        try {
            picBuffer = new byte[(int)Picture.length()];
            in = new DataInputStream(new FileInputStream(Picture));
            in.read(picBuffer);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public UUID getPictureUUID(){
        return this.pictureID;
    }
    
    public String getUserName(){
        return this.userName;
    }
    
    public String getTag(){
        return this.tag;
    }
    
    public byte[] getPicture(){
        return this.picBuffer;
    }
    
    public Integer getPermissions(){
        return this.permissions;
    }
}
