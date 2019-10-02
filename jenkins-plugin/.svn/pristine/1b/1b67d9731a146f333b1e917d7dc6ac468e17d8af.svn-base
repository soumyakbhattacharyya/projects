package hudson.plugins.projectstats.data.constant;
import hudson.plugins.projectstats.resource.ServiceLocator;

import java.util.ServiceLoader;

public final class QueryConstant {
	
	public static final String QUERY_BAAS_BUILD_LOG = (new StringBuffer())
														.append(" select 'ON_DEMAND', sum(duration_millisec), count(build_id) from ").append(ServiceLocator.getInstance().getAbsoluteTableName()).append(" where ")
														.append(" project_id = ? and job_id = ? and status = 'ACTIVE' and label like 'JIT%' ")
														.append(" union select 'ON_POOLED', sum(duration_millisec), count(build_id) from ").append(ServiceLocator.getInstance().getAbsoluteTableName()).append(" where ")
														.append(" project_id = ? and job_id = ? and status = 'ACTIVE' and label NOT like 'i-%' and label NOT like 'JIT%'")
														.append(" union select 'ON_THIRD_PARTY', sum(duration_millisec), count(build_id) from ").append(ServiceLocator.getInstance().getAbsoluteTableName()).append(" where ")
														.append(" project_id = ? and job_id = ? and status = 'ACTIVE' and label like 'i-%'")
														.append(" union select 'TOTAL', sum(duration_millisec), count(build_id) from ").append(ServiceLocator.getInstance().getAbsoluteTableName()).append(" where ")
														.append(" project_id = ? and job_id = ? and status = 'ACTIVE' ").toString();
	public static final String ON_DEMAND = "ON_DEMAND";
	public static final String ON_POOLED = "ON_POOLED";
	public static final String ON_THIRD_PARTY = "ON_THIRD_PARTY";
	public static final String TOTAL = "TOTAL";

}
