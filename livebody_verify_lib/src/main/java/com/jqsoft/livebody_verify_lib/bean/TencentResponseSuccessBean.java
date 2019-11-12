package com.jqsoft.livebody_verify_lib.bean;

/**
 * @author yedong
 * @date 2019/8/8
 * 腾讯云请求成功实体类
 */
public class TencentResponseSuccessBean {

    private ResponseBean Response;

    public ResponseBean getResponse() {
        return Response;
    }

    public void setResponse(ResponseBean Response) {
        this.Response = Response;
    }

    public static class ResponseBean {
        /**
         * Name :
         * Sex : 男
         * Nation : 汉
         * Birth : 1991/8/14
         * Address :
         * IdNum :
         * Authority :
         * ValidDate :
         * AdvancedInfo : {}
         * RequestId : 2fa0a5a4-a9cb-48f2-9022-55b79d742cb5
         */

        private String Name;
        private String Sex;
        private String Nation;
        private String Birth;
        private String Address;
        private String IdNum;
        private String Authority;
        private String ValidDate;
        private String AdvancedInfo;
        private String RequestId;
        private ErrorBean Error;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getNation() {
            return Nation;
        }

        public void setNation(String Nation) {
            this.Nation = Nation;
        }

        public String getBirth() {
            return Birth;
        }

        public void setBirth(String Birth) {
            this.Birth = Birth;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getIdNum() {
            return IdNum;
        }

        public void setIdNum(String IdNum) {
            this.IdNum = IdNum;
        }

        public String getAuthority() {
            return Authority;
        }

        public void setAuthority(String Authority) {
            this.Authority = Authority;
        }

        public String getValidDate() {
            return ValidDate;
        }

        public void setValidDate(String ValidDate) {
            this.ValidDate = ValidDate;
        }

        public String getAdvancedInfo() {
            return AdvancedInfo;
        }

        public void setAdvancedInfo(String AdvancedInfo) {
            this.AdvancedInfo = AdvancedInfo;
        }

        public String getRequestId() {
            return RequestId;
        }

        public void setRequestId(String RequestId) {
            this.RequestId = RequestId;
        }

        public ErrorBean getError() {
            return Error;
        }

        public void setError(ErrorBean error) {
            Error = error;
        }

        public static class ErrorBean {
            /**
             * Code : InvalidParameter
             * Message : The request content not valid json format for specified content-type=application/json.
             */

            private String Code;
            private String Message;

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getMessage() {
                return Message;
            }

            public void setMessage(String Message) {
                this.Message = Message;
            }
        }
    }
}
