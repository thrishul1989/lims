package com.todaysoft.lims.report.exception;

public interface ErrorCode {
	String INNER_ERROR = "Ex.Inner.Error";

	String OLD_PASSWORD_UNMATCHED = "Ex.Old.Password.Unmatched";

	String UPLOAD_MKDIR_FAILURE = "Ex.Upload.Mkdir.Failure";

	String UPLOAD_SUFFIX_UNSUPPORTED = "Ex.Upload.Suffix.Unsupported";

	String UPLOAD_IO_EXCEPTION = "Ex.Upload.io.Exception";
}
