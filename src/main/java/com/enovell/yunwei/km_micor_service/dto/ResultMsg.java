package com.enovell.yunwei.km_micor_service.dto;

import org.apache.commons.lang.StringUtils;

/**
 * kunmingTXD
 *
 * @author bili
 * @date 18-11-21
 */
public class ResultMsg {
        public static final String SUC_STATUS = "1";
        public static final String FAIL_STATUS = "2";
        private String status;
        private String msg;
        private Object data;

        public ResultMsg() {
        }

        public static ResultMsg getSuccess() {
            return getSuccess((String)null, (Object)null);
        }

        public static ResultMsg getFailure() {
            return getFailure((String)null, (Object)null);
        }

        public static ResultMsg getSuccess(Object data) {
            return getSuccess((String)null, data);
        }

        public static ResultMsg getSuccess(String msg) {
            return getSuccess(msg, (Object)null);
        }

        public static ResultMsg getFailure(String msg) {
            return getFailure(msg, (Object)null);
        }

        public static ResultMsg getSuccess(String msg, Object data) {
            ResultMsg result = new ResultMsg();
            if (StringUtils.isBlank(msg)) {
                msg = "操作成功！";
            }

            result.msg = msg;
            if (data != null) {
                result.data = data;
            }

            result.status = "1";
            return result;
        }

        public static ResultMsg getFailure(String msg, Object data) {
            ResultMsg result = new ResultMsg();
            if (StringUtils.isBlank(msg)) {
                msg = "系统错误，请联系管理员！";
            }

            result.msg = msg;
            if (data != null) {
                result.data = data;
            }

            result.status = "2";
            return result;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object data) {
            this.data = data;
        }

}
