//package com.tuisitang.projects.pm.modules.shop.dao;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import com.tuisitang.projects.pm.common.persistence.BaseDao;
//import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
//import com.tuisitang.projects.pm.modules.shop.entity.MessageQueue;
//
//public interface MessageQueueDao extends MessageQueueDaoCustom, CrudRepository<MessageQueue, Long> {
//	
//	@Query("from MessageQueue a order by a.operateTime")
//	List<MessageQueue> findByCustomerId(Long customerId);
//	
//	@Modifying
//	@Query("update MessageQueue set delFlag='" + MessageQueue.DEL_FLAG_DELETE + "' where id = ?1")
//	public int deleteById(Long id);
//	
//}
//
///**
// * DAO自定义接口
// */
//interface MessageQueueDaoCustom extends BaseDao<MessageQueue> {
//
//}
//
///**
// * DAO自定义接口实现
// */
//@Repository
//class MessageQueueDaoImpl extends BaseDaoImpl<MessageQueue> implements MessageQueueDaoCustom {
//	
//}
