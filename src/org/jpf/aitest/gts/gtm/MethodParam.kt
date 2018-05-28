package org.jpf.aitest.gts.gtm


class MethodParam {

	val ParamMoidfy = "";
	val ParamRealType = "";
	var ParamHideType = "";
	var ParamType = "";

	var ParamVariable = "";
	var ParamValue = "";
	var isArray = false;
	// 参数前初始化，如数据库使用classforname
	var ParamPreInit = "";
	var currentPackage = "";


	fun getParamMoidfy(): String {
		return ParamMoidfy;
	}
}