package net.jeeshop.web.action.manage.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.Services;
import net.jeeshop.core.system.bean.Menu;
import net.jeeshop.core.system.bean.MenuItem;
import net.jeeshop.core.system.bean.MenuType;
import net.jeeshop.core.system.bean.Privilege;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.system.impl.MenuService;
import net.jeeshop.services.manage.system.impl.PrivilegeService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.mapper.JsonMapper;


/**
 * 资源管理
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {
	private static final long serialVersionUID = 1L;
	private MenuService menuService;
	private PrivilegeService privilegeService;
	private static final String str = "../";
//	private Menu menu = new Menu();
	private static final Logger log = LoggerFactory.getLogger(MenuAction.class);
	
	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("menu!selectList.action");
	}
	/**
	 * 转到 添加/修改菜单 页面
	 * @return
	 * @throws Exception
	 */
	public String toAddOrUpdate() throws Exception{
		e.setId(new Long(getRequest().getParameter("id")));
		e = menuService.selectOne(e);
		log.info("{}", e);
		return "addOrUpdate";
	}
	/**
	 * 添加/修改菜单
	 * 修改选中的菜单，为该菜单添加子菜单
	 * @return
	 * @throws Exception
	 */
	public String addOrUpdate() throws Exception{
		//选中菜单的信息
		String updateP = getRequest().getParameter("updateP");
		Long id = new Long(getRequest().getParameter("id"));
		String name = getRequest().getParameter("name");
		String orderNum = getRequest().getParameter("orderNum");
		String type = getRequest().getParameter("type");
		
		//要添加的子菜单
		String url = getRequest().getParameter("url");
		String n_name = getRequest().getParameter("n_name");
		String n_url = getRequest().getParameter("n_url");
		String parentOrChild = getRequest().getParameter("parentOrChild");
		String n_orderNum = getRequest().getParameter("n_orderNum");
		String n_type = getRequest().getParameter("n_type");
		
		Menu itemMenu = null;
		if(n_name!=null && !n_name.trim().equals("")){
			itemMenu = new Menu();
			//添加子菜单
			if(parentOrChild.equals("0")){//顶级模块
				itemMenu.setPid(0L);
				itemMenu.setType(MenuType.module.toString());
			} else if(parentOrChild.equals("1")){//顶级页面
				itemMenu.setPid(0L);
				itemMenu.setType(MenuType.page.toString());
			} else if(parentOrChild.equals("2")){//子模块
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.module.toString());
			} else if(parentOrChild.equals("3")){//子页面
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.page.toString());
			} else if(parentOrChild.equals("4")){   //功能
				itemMenu.setPid(id);
				itemMenu.setType(MenuType.button.toString());
			} else {
				throw new IllegalAccessException("添加菜单异常。");
			}
			itemMenu.setName(n_name);
			itemMenu.setUrl(n_url);
			itemMenu.setOrderNum(Integer.valueOf(n_orderNum));
			itemMenu.setType(n_type);
		}
		//修改父菜单
		Menu m = new Menu();
		m.setId(id);
		m.setName(name);
		m.setUrl(url);
		m.setOrderNum(Integer.valueOf(orderNum));
		m.setType(type);
		
		menuService.addOrUpdate(updateP,m, itemMenu);
		
		getResponse().getWriter().print("ok");
		return null;
	}
	
	//加载指定角色的全部菜单
	public String selectJsonMenu() throws Exception {
		Object menusJson = getSession().getAttribute(ManageContainer.resource_menus);
		if(menusJson!=null){
			getResponse().getWriter().write(menusJson.toString());
		}else{
			User u = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
			List<MenuItem> root = loadMenus(u,0L,null);
			
			getSession().setAttribute(ManageContainer.resource_menus, writeMenus(root));
			
			//找出用户具有的功能，并且存放到session中，以方便后期的功能权限检查
//			if(root!=null){
//				for(int i=0;i<root.size();i++){
//					MenuItem item = root.get(i);
////					if(item.get)
//				}
////				getSession().setAttribute(ManageContainer.user_resource_menus_button,null);
//			}
		}
		log.info("{}", menusJson);
		return null;
	}
	
	/**
	 * 添加用户资源功能到session，为后面权限功能检查做铺垫
	 */
	private void addUserResourceMenusButton(String button){
		log.debug("addUserResourceMenusButton.button="+button);
		Map<String,String> buttons = (Map<String, String>) getSession().getAttribute(ManageContainer.user_resource_menus_button);
		if(buttons==null){
			buttons = new HashMap<String, String>();//TreeMap<String, String>();
			getSession().setAttribute(ManageContainer.user_resource_menus_button,buttons);
		}
		buttons.put(button, button);
	}
	
	/**
	 * 从PID=0开始加载菜单资源
	 * 获取指定节点的全部子菜单（包括当前菜单节点）
	 * @return
	 * @throws Exception
	 */
	public void getMenusByPid() throws Exception {
		Long pid = StringUtils.isBlank(getRequest().getParameter("pid")) ? 0L : new Long(getRequest().getParameter("pid"));
		Long id = StringUtils.isBlank(getRequest().getParameter("id")) ? null : new Long(getRequest().getParameter("id"));
		List<MenuItem> menus = menuService.loadMenus(null, pid, "#");
		
		// 加载全部的菜单
		if(id!=null){
			// 加载指定角色的权限
			Privilege privilege = new Privilege();
			privilege.setRid(id);
			List<Privilege> rolePs = privilegeService.selectList(privilege);
			
			// 拿角色拥有的菜单和全部的菜单做比对，进行勾选
			for (int i = 0; i < rolePs.size(); i++) {
				Privilege p = rolePs.get(i);
				eeee(p, menus);
			}
		}
		writeMenus(menus);
	}
	
	/**
	 * 角色权限和资源菜单进行对比，使checkbox选中
	 * @param p
	 * @param menus
	 */
	private void eeee(Privilege p,List<MenuItem> menus){
		for (int j = 0; j < menus.size(); j++) {
			MenuItem menu = menus.get(j);
			if (p.getMid().equals(menu.getId())) {
				menu.setChecked(true);
				return;
			}else{
				if(menu.getChildren()!=null && menu.getChildren().size()>0)
					eeee(p, menu.getChildren());
			}
		}
	}
	
	//输出菜单到页面
	private String writeMenus(List<MenuItem> root) throws IOException{
		JsonMapper mapper = JsonMapper.nonEmptyMapper();
//		JSONArray json = JSONArray.fromObject(root);
//		System.out.println(json.toString());
		String jsonStr = mapper.toJson(root);
		try {
			getResponse().getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
//		return mapper.toJson(root);
	}

	/**
	 * 根据角色ID，加载用户菜单资源
	 * @param u
	 * @param pid
	 * @param url
	 * @return
	 */
	private List<MenuItem> loadMenus(User u,Long pid,String url) {
		/*
		 * 首先，加载顶级目录或页面菜单 
		 */
		Map<String,Object> param = new HashMap<String, Object>();
		if(u!=null && u.getRid()!=null){
			param.put("rid", u.getRid());//角色ID
		}
		param.put("pid", pid);//菜单父ID
		List<Menu> menus = menuService.selectList(param);
		//创建菜单集合
		List<MenuItem> root = new ArrayList<MenuItem>();
		//循环添加菜单到菜单集合
		for (Iterator<Menu> it = menus.iterator(); it.hasNext();) {
			Menu entry = it.next();
			MenuItem item = new MenuItem(entry.getName(), null);
			item.setId(entry.getId().toString());
			item.setPid(entry.getPid().toString());
			item.setMenuType(entry);
//			if(item.getType().equals(MenuType.page)){
//				item.setIcon("http://127.0.0.1:8082/myshop/resource/images/letter.gif");
//			}
			if(url!=null){
				item.setUrl(url);
			}else{
				item.setUrl(entry.getUrl());
			}
			root.add(item);
		}
		
		/*
		 * 其次，加载子菜单 或 按钮功能
		 */
		for (int i = 0; i < root.size(); i++) {
			MenuItem item = root.get(i);
			if(!item.isButton()){
				Menu mm = new Menu();
				mm.setPid(new Long(item.getId()));
				loadChildrenByPid(root.get(i), mm,url,u);
			}else{
				//addUserResourceMenusButton(item.getUrl());
			}
		}

		return root;
	}

	// 根据父ID加载子节点
	private void loadChildrenByPid(MenuItem item, Menu menu,String url,User u) {
		Map<String,Object> param = new HashMap<String, Object>();
		
		if(u!=null && u.getRid()!=null)
			param.put("rid", u.getRid());
		param.put("pid", menu.getPid());
		//加载菜单节点
		List<Menu> data = menuService.selectList(param);
		if(data==null || data.size()==0){
			return;
		}
		if(item.getChildren()==null)item.setChildren(new ArrayList<MenuItem>());
		//创建菜单节点
		for (int i = 0; i < data.size(); i++) {
			Menu entry = data.get(i);
			
			MenuItem addItem = new MenuItem(entry.getName(), null);
			addItem.setId(entry.getId().toString());
			addItem.setPid(entry.getPid().toString());
			addItem.setMenuType(entry);
			String url0 = null;
			if(url!=null){
				addItem.setUrl(str+url);
				url0 = url;
			}else{
				addItem.setUrl(str+entry.getUrl());
				url0 = entry.getUrl();
			}
//			System.out.println("entry.getType()="+entry.getType()+",MenuType.button="+MenuType.button);
			if(entry.getType().equals("button")){
				addUserResourceMenusButton(url0);
			}else{
				item.getChildren().add(addItem);
			}
		}
		//根据菜单节点进行递归加载
		for (int i = 0; i < item.getChildren().size(); i++) {
			MenuItem childItem = item.getChildren().get(i);
			if(!childItem.isButton()){
				Menu itemMenu = new Menu();
				itemMenu.setPid(new Long(childItem.getId()));
//				itemMenu.setMenuType(entry);
				loadChildrenByPid(childItem, itemMenu,url,u);
			}
		}
	}

	public String save() throws Exception {
		if (e.getId() == null || e.getId().equals("")) {
			if (e.getUrl() == null) {
				e.setUrl("");
			}
			menuService.insert(e);
		} else {
			menuService.update(e);
		}
		return selectList();
	}

	/**
	 * 删除菜单
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		
		String ids = getRequest().getParameter("ids");
		if(ids==null || ids.trim().equals(""))
				throw new Exception("删除菜单异常！");
		
		this.menuService.deletes(ids,getRequest().getParameter("deleteParent"));
		
		//删除成功返回1
		getResponse().getWriter().println("1");
		return null;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

//	public Menu getMenu() {
//		return menu;
//	}
//
//	public void setMenu(Menu menu) {
//		this.menu = menu;
//	}
//
	@Override
	public Menu getE() {
		// TODO Auto-generated method stub
		return this.e;
	}

	@Override
	public Services<Menu> getServer() {
		return this.menuService;
	}

	@Override
	public void prepare() throws Exception {
		if(e==null){
			e = new Menu();
		} 
	}

	@Override
	public void insertAfter(Menu e) {
		// TODO Auto-generated method stub
		e.clear();
	}
	
}
