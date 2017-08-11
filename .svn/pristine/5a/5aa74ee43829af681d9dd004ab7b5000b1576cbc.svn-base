package com.tiamaes.bike.api.information.paths.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiamaes.bike.common.bean.information.Pathpoint;

/**
 * @author kangty
 * 场区线路坐标集持久层接口
 */
public interface PathsMapper {
	
	/**
	 * 根据编号查询坐标集信息
	 * @param targetid
	 * @return
	 */
	List<Pathpoint> getPathsByTargetId(@Param("targetid")int targetid);
	
	/**
	 * 新增坐标集信息
	 * @param targetid
	 * @param paths
	 */
	void addPaths(List<Pathpoint> paths);
	
	/**
	 * 删除坐标集信息
	 * @param targetid
	 */
	void deletePaths(@Param("targetid")int targetid);

	/**
	 * 更新坐标集信息
	 * @param pathpoint
	 */
	void updatePathpoint(Pathpoint pathpoint);

}
