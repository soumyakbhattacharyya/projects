package com.cognizant.formit.controller;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cognizant.formit.model.Attribute;
import com.cognizant.formit.model.NodeResource;
import com.cognizant.formit.model.Project;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.util.PropertyBinderUtil;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;

@Controller
@RequestMapping("/resource")
public class NodeController {

	@RequestMapping(value = "/resources", method = RequestMethod.GET)
	public Project getResources() throws PropertyVetoException, SQLException,
			ClassNotFoundException {

		return returnData();
	}

	private Project returnData() throws PropertyVetoException, SQLException,
			ClassNotFoundException {

		ArrayList<NodeResource> names = new ArrayList<NodeResource>();

		// names.add(new NodeResource());

		NodeResource nodeResource = new NodeResource();
		Project project = new Project();
		// project.setNodeResource(nodeResource);
		NodeResource serverNode = new NodeResource();
		project.setList(getNodeResources(project));

		return project;
	}

	List<NodeResource> getNodeResources(Project project)
			throws PropertyVetoException, SQLException, ClassNotFoundException {

		DBHelper target = DBHelper
				.newInstance(new IConnectionPoolInitializer() {
					private String dbUrl = PropertyBinderUtil
							.getPIPBinderInstance().rundeckPublishDbUrl();
					private String dbDriverClass = PropertyBinderUtil
							.getPIPBinderInstance()
							.rundeckPublishDbDriverClass();
					private String dbUid = PropertyBinderUtil
							.getPIPBinderInstance().rundeckPublishDbUid();
					private String dbPwd = PropertyBinderUtil
							.getPIPBinderInstance().rundeckPublishDbPwd();
					private int dbInitialPoolSize = Integer
							.parseInt(PropertyBinderUtil.getPIPBinderInstance()
									.rundeckPublishDbInitialPoolSize());
					private int dbMinPoolSize = Integer
							.parseInt(PropertyBinderUtil.getPIPBinderInstance()
									.rundeckPublishDbMinPoolSize());
					private int dbMaxPoolSize = Integer
							.parseInt(PropertyBinderUtil.getPIPBinderInstance()
									.rundeckPublishDbMaxPoolSize());
					private int dbMaxIdleTime = Integer
							.parseInt(PropertyBinderUtil.getPIPBinderInstance()
									.rundeckPublishDbMaxIdleTime());

					@Override
					public String dbUrl() {
						return dbUrl;
					}

					@Override
					public String dbDriverClass() {
						return dbDriverClass;
					}

					@Override
					public String dbUid() {
						return dbUid;
					}

					@Override
					public String dbPwd() {
						return dbPwd;
					}

					@Override
					public int dbMaxPoolSize() {
						return dbMaxPoolSize;
					}

					@Override
					public int dbMinPoolSize() {
						return dbMinPoolSize;
					}

					@Override
					public int dbInitialPoolSize() {
						return dbInitialPoolSize;
					}

					@Override
					public int dbMaxIdleTime() {
						return dbMaxIdleTime;
					}
				});
		List<RNode> list = target.findAll();
		Iterator<RNode> iterator = list.iterator();
		List<NodeResource> nodeResources = new ArrayList<NodeResource>();
		while (iterator.hasNext()) {
			NodeResource nodeResource = new NodeResource();
			RNode node = iterator.next();
			BeanUtils.copyProperties(node, nodeResource,
					new String[] { "attribute" });
			if (node != null && node.getAttribute() != null) {
				Attribute resourceAttribute = new Attribute();
				String nodeResult = node.getAttribute();
				String[] attributeList =nodeResult.split(",");
				for (String attribute : attributeList) {
					if (attribute.startsWith("name")) {
						resourceAttribute.setName(attribute.substring("name".length()+1));
					} else if (attribute.startsWith("value")) {
						resourceAttribute.setValue(attribute.substring("value".length()+1));
					}
				}
				nodeResource.setAttribute(resourceAttribute);
			}
			nodeResources.add(nodeResource);
		}

		return nodeResources;

	}
}
