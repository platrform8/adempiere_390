/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Yamel Senih, ERPCyA http://www.erpcya.com                  *
 *****************************************************************************/
package org.adempiere.util;

/**
 *	@author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *		<li> FR [ 94 ] "IsDocument" flag in table for create default columns
 *		@see https://github.com/adempiere/adempiere/issues/94
 */
public final class DocumentTemplate {

	/**	Class Body for document	*/
	public static final String BODY = 
			"\t/**\n"
			+ "\t * \tGet Document Info\n"
			+ "\t *\t@return document info (untranslated)\n"
			+ "\t */\n"
			+ "\tpublic String getDocumentInfo()\n"
			+ "\t{\n"
			+ "\t\tMDocType dt = MDocType.get(getCtx(), getC_DocType_ID());\n"
			+ "\t\treturn dt.getName() + \" \" + getDocumentNo();\n"
			+ "\t}\t//\tgetDocumentInfo\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tCreate PDF\n"
			+ "\t *\t@return File or null\n"
			+ "\t */\n"
			+ "\tpublic File createPDF ()\n"
			+ "\t{\n"
			+ "\t\ttry\n"
			+ "\t\t{\n"
			+ "\t\t\tFile temp = File.createTempFile(get_TableName() + get_ID() +\"_\", \".pdf\");\n"
			+ "\t\t\treturn createPDF (temp);\n"
			+ "\t\t}\n"
			+ "\t\tcatch (Exception e)\n"
			+ "\t\t{\n"
			+ "\t\t\tlog.severe(\"Could not create PDF - \" + e.getMessage());\n"
			+ "\t\t}\n"
			+ "\t\treturn null;\n"
			+ "\t}\t//\tgetPDF\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tCreate PDF file\n"
			+ "\t *\t@param file output file\n"
			+ "\t *\t@return file if success\n"
			+ "\t */\n"
			+ "\tpublic File createPDF (File file)\n"
			+ "\t{\n"
			+ "\t//\tReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());\n"
			+ "\t//\tif (re == null)\n"
			+ "\t\t\treturn null;\n"
			+ "\t//\treturn re.getPDF(file);\n"
			+ "\t}\t//\tcreatePDF\n"
			+ "\n"
			+ "\t\n"
			+ "\t/**************************************************************************\n"
			+ "\t * \tProcess document\n"
			+ "\t *\t@param processAction document action\n"
			+ "\t *\t@return true if performed\n"
			+ "\t */\n"
			+ "\tpublic boolean processIt (String processAction)\n"
			+ "\t{\n"
			+ "\t\tm_processMsg = null;\n"
			+ "\t\tDocumentEngine engine = new DocumentEngine (this, getDocStatus());\n"
			+ "\t\treturn engine.processIt (processAction, getDocAction());\n"
			+ "\t}\t//\tprocessIt\n"
			+ "\t\n"
			+ "\t/**\tProcess Message \t\t\t*/\n"
			+ "\tprivate String\t\tm_processMsg = null;\n"
			+ "\t/**\tJust Prepared Flag\t\t\t*/\n\tprivate boolean\t\tm_justPrepared = false;\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tUnlock Document.\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean unlockIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"unlockIt - \" + toString());\n"
			+ "\t//\tsetProcessing(false);\n"
			+ "\t\treturn true;\n"
			+ "\t}\t//\tunlockIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tInvalidate Document\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean invalidateIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"invalidateIt - \" + toString());\n"
			+ "\t\tsetDocAction(DOCACTION_Prepare);\n"
			+ "\t\treturn true;\n"
			+ "\t}\t//\tinvalidateIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t *\tPrepare Document\n"
			+ "\t * \t@return new status (In Progress or Invalid) \n"
			+ "\t */\n"
			+ "\tpublic String prepareIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(toString());\n"
			+ "\t\tm_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);\n"
			+ "\t\tif (m_processMsg != null)\n"
			+ "\t\t\treturn DocAction.STATUS_Invalid;\n"
			+ "\t\t\n"
			+ "\t\tMDocType dt = MDocType.get(getCtx(), getC_DocType_ID());\n"
			+ "\n"
			+ "\t\t//\tStd Period open?\n"
			+ "\t\tif (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID()))\n"
			+ "\t\t{\n"
			+ "\t\t\tm_processMsg = \"@PeriodClosed@\";\n"
			+ "\t\t\treturn DocAction.STATUS_Invalid;\n"
			+ "\t\t}\n"
			+ "\t\t//\tAdd up Amounts\n"
			+ "\t\tm_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);\n"
			+ "\t\tif (m_processMsg != null)\n"
			+ "\t\t\treturn DocAction.STATUS_Invalid;\n"
			+ "\t\tm_justPrepared = true;\n"
			+ "\t\tif (!DOCACTION_Complete.equals(getDocAction()))\n"
			+ "\t\t\tsetDocAction(DOCACTION_Complete);\n"
			+ "\t\treturn DocAction.STATUS_InProgress;\n"
			+ "\t}\t//\tprepareIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tApprove Document\n\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean  approveIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"approveIt - \" + toString());\n"
			+ "\t\tsetIsApproved(true);\n"
			+ "\t\treturn true;\n"
			+ "\t}\t//\tapproveIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tReject Approval\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean rejectIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"rejectIt - \" + toString());\n"
			+ "\t\tsetIsApproved(false);\n"
			+ "\t\treturn true;\n\t}\t//\trejectIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tComplete Document\n"
			+ "\t * \t@return new status (Complete, In Progress, Invalid, Waiting ..)\n"
			+ "\t */\n"
			+ "\tpublic String completeIt()\n"
			+ "\t{\n"
			+ "\t\t//\tRe-Check\n"
			+ "\t\tif (!m_justPrepared)\n"
			+ "\t\t{\n"
			+ "\t\t\tString status = prepareIt();\n"
			+ "\t\t\tif (!DocAction.STATUS_InProgress.equals(status))\n"
			+ "\t\t\t\treturn status;\n"
			+ "\t\t}\n"
			+ "\n"
			+ "\t\tm_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);\n"
			+ "\t\tif (m_processMsg != null)\n"
			+ "\t\t\treturn DocAction.STATUS_Invalid;\n"
			+ "\t\t\n"
			+ "\t\t//\tImplicit Approval\n"
			+ "\t\tif (!isApproved())\n"
			+ "\t\t\tapproveIt();\n"
			+ "\t\tlog.info(toString());\n"
			+ "\t\t//\n"
			+ "\t\t\n"
			+ "\t\t//\tUser Validation\n"
			+ "\t\tString valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);\n"
			+ "\t\tif (valid != null)\n"
			+ "\t\t{\n"
			+ "\t\t\tm_processMsg = valid;\n"
			+ "\t\t\treturn DocAction.STATUS_Invalid;\n"
			+ "\t\t}\n"
			+ "\t\t//\tSet Definitive Document No\n"
			+ "\t\tsetDefiniteDocumentNo();\n"
			+ "\n"
			+ "\t\tsetProcessed(true);\n"
			+ "\t\tsetDocAction(DOCACTION_Close);\n"
			+ "\t\treturn DocAction.STATUS_Completed;\n"
			+ "\t}\t//\tcompleteIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tSet the definite document number after completed\n"
			+ "\t */\n"
			+ "\tprivate void setDefiniteDocumentNo() {\n"
			+ "\t\tMDocType dt = MDocType.get(getCtx(), getC_DocType_ID());\n"
			+ "\t\tif (dt.isOverwriteDateOnComplete()) {\n"
			+ "\t\t\tsetDateDoc(new Timestamp(System.currentTimeMillis()));\n"
			+ "\t\t}\n"
			+ "\t\tif (dt.isOverwriteSeqOnComplete()) {\n"
			+ "\t\t\tString value = null;\n"
			+ "\t\t\tint index = p_info.getColumnIndex(\"C_DocType_ID\");\n"
			+ "\t\t\tif (index == -1)\n"
			+ "\t\t\t\tindex = p_info.getColumnIndex(\"C_DocTypeTarget_ID\");\n"
			+ "\t\t\tif (index != -1)\t\t//\tget based on Doc Type (might return null)\n"
			+ "\t\t\t\tvalue = DB.getDocumentNo(get_ValueAsInt(index), get_TrxName(), true);\n"
			+ "\t\t\tif (value != null) {\n"
			+ "\t\t\t\tsetDocumentNo(value);\n"
			+ "\t\t\t}\n"
			+ "\t\t}\n"
			+ "\t}\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tVoid Document.\n"
			+ "\t * \tSame as Close.\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean voidIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"voidIt - \" + toString());\n"
			+ "\t\treturn closeIt();\n"
			+ "\t}\t//\tvoidIt\n\t\n\t/**\n"
			+ "\t * \tClose Document.\n"
			+ "\t * \tCancel not delivered Qunatities\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean closeIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"closeIt - \" + toString());\n"
			+ "\n"
			+ "\t\t//\tClose Not delivered Qty\n"
			+ "\t\tsetDocAction(DOCACTION_None);\n"
			+ "\t\treturn true;\n"
			+ "\t}\t//\tcloseIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tReverse Correction\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean reverseCorrectIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"reverseCorrectIt - \" + toString());\n"
			+ "\t\treturn false;\n"
			+ "\t}\t//\treverseCorrectionIt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tReverse Accrual - none\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean reverseAccrualIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"reverseAccrualIt - \" + toString());\n"
			+ "\t\treturn false;\n"
			+ "\t}\t//\treverseAccrualIt\n"
			+ "\t\n"
			+ "\t/** \n"
			+ "\t * \tRe-activate\n"
			+ "\t * \t@return true if success \n"
			+ "\t */\n"
			+ "\tpublic boolean reActivateIt()\n"
			+ "\t{\n"
			+ "\t\tlog.info(\"reActivateIt - \" + toString());\n"
			+ "\t\tsetProcessed(false);\n"
			+ "\t\tif (reverseCorrectIt())\n"
			+ "\t\t\treturn true;\n"
			+ "\t\treturn false;\n"
			+ "\t}\t//\treActivateIt\n"
			+ "\t\n"
			+ "\t\n"
			+ "\t/*************************************************************************\n"
			+ "\t * \tGet Summary\n"
			+ "\t *\t@return Summary of Document\n"
			+ "\t */\n"
			+ "\tpublic String getSummary()\n"
			+ "\t{\n"
			+ "\t\tStringBuffer sb = new StringBuffer();\n"
			+ "\t\tsb.append(getDocumentNo());\n"
			+ "\t//\tsb.append(\": \")\n"
			+ "\t//\t\t.append(Msg.translate(getCtx(),\"TotalLines\")).append(\"=\").append(getTotalLines())\n"
			+ "\t//\t\t.append(\" (#\").append(getLines(false).length).append(\")\");\n"
			+ "\t\t//\t - Description\n"
			+ "\t\tif (getDescription() != null && getDescription().length() > 0)\n"
			+ "\t\t\tsb.append(\" - \").append(getDescription());\n"
			+ "\t\treturn sb.toString();\n"
			+ "\t}\t//\tgetSummary\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tGet Process Message\n"
			+ "\t *\t@return clear text error message\n"
			+ "\t */\n"
			+ "\tpublic String getProcessMsg()\n"
			+ "\t{\n"
			+ "\t\treturn m_processMsg;\n"
			+ "\t}\t//\tgetProcessMsg\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tGet Document Owner (Responsible)\n"
			+ "\t *\t@return AD_User_ID\n"
			+ "\t */\n"
			+ "\tpublic int getDoc_User_ID()\n"
			+ "\t{\n"
			+ "\t//\treturn getSalesRep_ID();\n"
			+ "\t\treturn 0;\n"
			+ "\t}\t//\tgetDoc_User_ID\n"
			+ "\n"
			+ "\t/**\n"
			+ "\t * \tGet Document Approval Amount\n"
			+ "\t *\t@return amount\n"
			+ "\t */\n"
			+ "\tpublic BigDecimal getApprovalAmt()\n"
			+ "\t{\n"
			+ "\t\treturn null;\t//getTotalLines();\n"
			+ "\t}\t//\tgetApprovalAmt\n"
			+ "\t\n"
			+ "\t/**\n"
			+ "\t * \tGet Document Currency\n"
			+ "\t *\t@return C_Currency_ID\n"
			+ "\t */\n"
			+ "\tpublic int getC_Currency_ID()\n"
			+ "\t{\n"
			+ "\t//\tMPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());\n"
			+ "\t//\treturn pl.getC_Currency_ID();\n"
			+ "\t\treturn 0;\n"
			+ "\t}\t//\tgetC_Currency_ID";
}