package cn.abelib.biz.controller.admin;


import cn.abelib.st.core.data.redis.RedisStringService;
import cn.abelib.biz.constant.StatusConstant;
import cn.abelib.st.core.result.Response;
import cn.abelib.st.core.utils.CookieUtil;
import cn.abelib.st.core.utils.JsonUtil;
import cn.abelib.biz.pojo.User;
import cn.abelib.biz.service.OrderService;
import cn.abelib.biz.service.UserService;
import cn.abelib.biz.vo.OrderVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author abel
 * @date 2017/12/12
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisStringService redisStringService;

    @PostMapping("/list.do")
    public Response<PageInfo> getOrderList(HttpServletRequest request,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        String token = CookieUtil.readToken(request);
        if (StringUtils.isEmpty(token)){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        String userJson = redisStringService.get(token);
        User user = JsonUtil.str2Obj(userJson, User.class);
        if (user == null){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.getOrderList(pageNum, pageSize);
        }else {
            return Response.failed(StatusConstant.NOT_ADMIN_ERROR);
        }
    }

    @PostMapping("/detail.do")
    public Response<OrderVo> getOrderDetail(HttpServletRequest request, Long orderNo){
        String token = CookieUtil.readToken(request);
        if (StringUtils.isEmpty(token)){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        String userJson = redisStringService.get(token);
        User user = JsonUtil.str2Obj(userJson, User.class);
        if (user == null){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.getOrderDetail(orderNo);
        }else {
            return Response.failed(StatusConstant.NOT_ADMIN_ERROR);
        }
    }

    @PostMapping("/cn.abelib.st.core.data.search.do")
    public Response<PageInfo> orderSearch(HttpServletRequest request, Long orderNo,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        String token = CookieUtil.readToken(request);
        if (StringUtils.isEmpty(token)){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        String userJson = redisStringService.get(token);
        User user = JsonUtil.str2Obj(userJson, User.class);
        if (user == null){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.orderSearch(orderNo, pageNum, pageSize);
        }else {
            return Response.failed(StatusConstant.NOT_ADMIN_ERROR);
        }
    }

    /**
     *  发货
     * @param request
     * @param orderNo
     * @return
     */
    @PostMapping("/send.do")
    public Response sendGoods(HttpServletRequest request, Long orderNo){
        String token = CookieUtil.readToken(request);
        if (StringUtils.isEmpty(token)){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        String userJson = redisStringService.get(token);
        User user = JsonUtil.str2Obj(userJson, User.class);
        if (user == null){
            return Response.failed(StatusConstant.USER_NOT_LOGIN);
        }
        if (userService.checkAdminRole(user).isSuccess()){
            return orderService.sendGoods(orderNo);
        }else {
            return Response.failed(StatusConstant.NOT_ADMIN_ERROR);
        }
    }
}
