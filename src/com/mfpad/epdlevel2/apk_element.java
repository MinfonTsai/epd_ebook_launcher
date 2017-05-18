package com.mfpad.epdlevel2;


public class apk_element {

    private String name;
    private String userid;
    private String classname;
    private String packagename;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the Uid
     */
    public String getUid() {
        return userid;
    }

    /**
     * @param Uid to set
     */
    public void setUid( String userid) {
        this.userid = userid;
    }
    
    /**
     * @return the Class name
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param Class name to set
     */
    public void setClassname( String name) {
        this.classname = name;
    }
    
    /**
     * @return the package name
     */
    public String getPackagename() {
        return packagename;
    }

    /**
     * @param Package name to set
     */
    public void setPackagename( String name) {
        this.packagename = name;
    }
    
}
