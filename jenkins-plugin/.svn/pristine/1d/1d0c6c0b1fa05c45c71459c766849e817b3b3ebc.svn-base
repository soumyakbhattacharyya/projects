// the goal is to find the top level job which should contain the changelist
def upstreamBuild = null
def cause = build.causes.find {
    if(it instanceof hudson.model.Cause.UpstreamCause) {
        return true 
    }
    return false
}

try {
    while(cause != null) {
        upstreamBuild = hudson.model.Hudson.instance.getItem(cause.upstreamProject).getBuildByNumber(cause.upstreamBuild)
        if(upstreamBuild == null) {
            break;
        }
        cause = upstreamBuild.causes.find {
            if(it instanceof hudson.model.Cause.UpstreamCause) {
                return true 
            }
            return false
        }
    }   
} catch(e) {
    // do nothing
}

// now we loop through the changeset and add all the users to a list
committers = []

if(upstreamBuild != null && upstreamBuild.changeSet != null) {
    upstreamBuild.changeSet.each() { cs ->
        if(cs.user != null) {
            committers.add(cs.user)
        }
    }
}

committers.unique().join(',')
