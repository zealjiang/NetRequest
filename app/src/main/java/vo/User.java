package vo;

import java.io.Serializable;

/**
 * 登录返回对象
 * @author zealjiang
 * @time 2016/11/15 14:26
 */
public class User implements Serializable {
    private int UserId;
    private String NickName;
    private int CityId;
    private String HeadPic;
    private String ProvinceId;
    private String CityShortName;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int CityId) {
        this.CityId = CityId;
    }

    public String getHeadPic() {
        return HeadPic;
    }

    public void setHeadPic(String HeadPic) {
        this.HeadPic = HeadPic;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getCityShortName() {
        return CityShortName;
    }

    public void setCityShortName(String cityShortName) {
        CityShortName = cityShortName;
    }
}
