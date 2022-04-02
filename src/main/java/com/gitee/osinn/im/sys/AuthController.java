package com.gitee.osinn.im.sys;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.osinn.im.common.R;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 描述
 *
 * @author wency_cai
 */
@RestController
public class AuthController {
    @Resource
    @Qualifier(value = "imUserService")
    private IImUserService imUserService;

    @Data
    public class Param {
        String account;
        String password;
    }

    @RequestMapping("/kefu/login")
    public R login(@RequestBody Param param) {
//
        ImUser imUser = imUserService.getByLoginName(param.getAccount());
        String token = MD5.create().digestHex(imUser.getId() + imUser.getLoginName());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        jsonObject.put("kefuInfo", imUser);
        return R.success(jsonObject);
    }

    @RequestMapping("/kefu/user/record")
    public R record() {
        String str = "[{\"id\":26013,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":29120,\"nickname\":\"游客2022536541\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1648188571,\"update_time\":1648188571,\"delete_time\":null,\"mssage_num\":0,\"message\":\"订单\",\"message_type\":1,\"user\":{\"id\":29120,\"nickname\":\"游客2022536541\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-25 14:09\"},{\"id\":24571,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":1,\"nickname\":\"大黑\",\"avatar\":\"https://chat.crmeb.net/uploads/attach/2021/09/20210906/c79d19dbfda66026ec891d188386cbb7.png\",\"is_tourist\":0,\"online\":0,\"type\":0,\"add_time\":1640768541,\"update_time\":1648188551,\"delete_time\":null,\"mssage_num\":0,\"message\":\"顶顶顶\",\"message_type\":1,\"user\":{\"id\":1,\"nickname\":\"大黑\",\"remark_nickname\":\"大黑\",\"version\":\"\"},\"_update_time\":\"2022-03-25 14:09\"},{\"id\":24545,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":27090,\"nickname\":\"游客2021294660\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/fcc758713087632dc785fff3d37db928.png\",\"is_tourist\":1,\"online\":0,\"type\":1,\"add_time\":1640680964,\"update_time\":1648146637,\"delete_time\":null,\"mssage_num\":0,\"message\":\"\",\"message_type\":1,\"user\":{\"id\":27090,\"nickname\":\"游客2021294660\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-25 02:30\"},{\"id\":25961,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":29039,\"nickname\":\"游客2022803397\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1648000336,\"update_time\":1648133779,\"delete_time\":null,\"mssage_num\":0,\"message\":\"2222222222\",\"message_type\":1,\"user\":{\"id\":29039,\"nickname\":\"游客2022803397\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-24 22:56\"},{\"id\":25271,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28156,\"nickname\":\"游客2022156849\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/1b244797f8b86b4cc0665d75d160aa30.png\",\"is_tourist\":0,\"online\":0,\"type\":0,\"add_time\":1645466565,\"update_time\":1648107880,\"delete_time\":null,\"mssage_num\":0,\"message\":\"看着不错啊\",\"message_type\":1,\"user\":{\"id\":28156,\"nickname\":\"游客2022156849\",\"remark_nickname\":\"游客2022156849\",\"version\":\"\"},\"_update_time\":\"2022-03-24 15:44\"},{\"id\":25963,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":29041,\"nickname\":\"游客2022213887\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/1b244797f8b86b4cc0665d75d160aa30.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1648003012,\"update_time\":1648020913,\"delete_time\":null,\"mssage_num\":0,\"message\":\"1111\",\"message_type\":1,\"user\":{\"id\":29041,\"nickname\":\"游客2022213887\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-23 15:35\"},{\"id\":24661,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":27304,\"nickname\":\"wang\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":0,\"online\":0,\"type\":0,\"add_time\":1641484197,\"update_time\":1648016280,\"delete_time\":null,\"mssage_num\":0,\"message\":\"33\",\"message_type\":1,\"user\":{\"id\":27304,\"nickname\":\"wang\",\"remark_nickname\":\"wang\",\"version\":\"\"},\"_update_time\":\"2022-03-23 14:18\"},{\"id\":25852,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28888,\"nickname\":\"游客2022322577\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/1b244797f8b86b4cc0665d75d160aa30.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647697978,\"update_time\":1647991782,\"delete_time\":null,\"mssage_num\":0,\"message\":\"几率\",\"message_type\":1,\"user\":{\"id\":28888,\"nickname\":\"游客2022322577\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-23 07:29\"},{\"id\":25947,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":29020,\"nickname\":\"游客2022618843\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647954293,\"update_time\":1647954386,\"delete_time\":null,\"mssage_num\":0,\"message\":\"\",\"message_type\":1,\"user\":{\"id\":29020,\"nickname\":\"游客2022618843\",\"remark_nickname\":\"游客2022618843\",\"version\":\"\"},\"_update_time\":\"2022-03-22 21:06\"},{\"id\":25927,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28993,\"nickname\":\"游客2022814833\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/1b244797f8b86b4cc0665d75d160aa30.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647930310,\"update_time\":1647939452,\"delete_time\":null,\"mssage_num\":0,\"message\":\"\",\"message_type\":1,\"user\":{\"id\":28993,\"nickname\":\"游客2022814833\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-22 16:57\"},{\"id\":25664,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28630,\"nickname\":\"/system/images/user_log.jpg\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647024162,\"update_time\":1647911616,\"delete_time\":null,\"mssage_num\":0,\"message\":\"undefinedundefinedundefined\",\"message_type\":1,\"user\":{\"id\":28630,\"nickname\":\"/system/images/user_log.jpg\",\"remark_nickname\":\"/system/images/user_log.jpg\",\"version\":\"\"},\"_update_time\":\"2022-03-22 09:13\"},{\"id\":25084,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":27849,\"nickname\":\"游客2022421750\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/fcc758713087632dc785fff3d37db928.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1644388915,\"update_time\":1647911593,\"delete_time\":null,\"mssage_num\":0,\"message\":\"权威的\",\"message_type\":1,\"user\":{\"id\":27849,\"nickname\":\"游客2022421750\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-22 09:13\"},{\"id\":24638,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":27253,\"nickname\":\"游客2022309832\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/fcc758713087632dc785fff3d37db928.png\",\"is_tourist\":0,\"online\":0,\"type\":0,\"add_time\":1641366780,\"update_time\":1647884109,\"delete_time\":null,\"mssage_num\":0,\"message\":\"sdfasdf\",\"message_type\":1,\"user\":{\"id\":27253,\"nickname\":\"游客2022309832\",\"remark_nickname\":\"游客2022309832\",\"version\":\"\"},\"_update_time\":\"2022-03-22 01:35\"},{\"id\":25887,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28945,\"nickname\":\"44游客\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647828804,\"update_time\":1647828804,\"delete_time\":null,\"mssage_num\":0,\"message\":\"\",\"message_type\":1,\"user\":{\"id\":28945,\"nickname\":\"44游客\",\"remark_nickname\":\"44游客\",\"version\":\"\"},\"_update_time\":\"2022-03-21 10:13\"},{\"id\":25874,\"appid\":\"202116257358989495\",\"user_id\":27036,\"to_user_id\":28914,\"nickname\":\"游客2022892534\",\"avatar\":\"https://demo40.crmeb.net/uploads/attach/2020/11/20201110/d4398c5d36757c1b1ed1f21202bea1c0.png\",\"is_tourist\":1,\"online\":0,\"type\":0,\"add_time\":1647754454,\"update_time\":1647754454,\"delete_time\":null,\"mssage_num\":0,\"message\":\"\",\"message_type\":1,\"user\":{\"id\":28914,\"nickname\":\"游客2022892534\",\"remark_nickname\":\"\",\"version\":\"\"},\"_update_time\":\"2022-03-20 13:34\"}]";
        JSONArray objects = JSON.parseArray(str);
        return R.success(objects);
    }
}
