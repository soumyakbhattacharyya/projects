/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck;

import com.cognizant.formit.chain.ChainExecutionContext;
import org.apache.tools.ant.Project;

/**
 * value that can be used to build options
 *
 * @author cognizant
 */
public interface Value {

	String get();

	Argument getArgument();
}
