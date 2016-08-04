package com.tuisitang.common.bo;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**    
 * @{#} IpInfo.java  
 * 
 * IP信息，根据淘宝的IP信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class IpInfo {

	private String country;// 国家中文名称
	private String countryId;// 国家id，中国 CN
	private String area;// 地区 华东 华南
	private Long areaId;// 地区id
	private String region;// 省份
	private Long regionId;// 省份id
	private String city;// 城市
	private Long cityId;// 城市id
	private String county;// 城镇
	private Long countyId;// 城镇id
	private String isp;// 运营商
	private Long ispId;// 运营商id
	private String ip;// ip

	public IpInfo(Map<String, String> ipMap) {
		super();
		this.country = ipMap.get("country");
		this.countryId = ipMap.get("country_id");
		this.area = ipMap.get("area");
		this.areaId = new Long(ipMap.get("area_id"));
		this.region = ipMap.get("region");
		this.regionId = new Long(ipMap.get("region_id"));
		this.city = ipMap.get("city");
		this.cityId = new Long(ipMap.get("city_id"));
		this.county = ipMap.get("county");
		this.countyId = new Long(ipMap.get("county_id"));
		this.isp = ipMap.get("isp");
		this.ispId = new Long(ipMap.get("isp_id"));
		this.ip = ipMap.get("ip");
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public Long getIspId() {
		return ispId;
	}

	public void setIspId(Long ispId) {
		this.ispId = ispId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
