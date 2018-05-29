package com.jlkf.ego.widget.spiner;
/**
 * 下拉框实体类
 * @author  邓超桂
 * @date 创建时间：2016-8-20 下午12:16:08
 */
public class ItemInfo {
	private String objId;
	private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountrName() {
		return countrName;
	}

	public void setCountrName(String countrName) {
		this.countrName = countrName;
	}

	private String countrName;
	private int drawable;

	public int getDrawable() {return drawable;}
	public void setDrawable(int drawable) {this.drawable = drawable;}
	private boolean selectFlag;
	public ItemInfo() {
		super();
	}
	public ItemInfo(String objId, String name) {
		super();
		this.objId = objId;
		this.name = name;
	}

	@Override
	public String toString() {
		return "ItemInfo [objId=" + objId + ", name=" + name + "]";
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(boolean selectFlag) {
		this.selectFlag = selectFlag;
	}
}

