package cn.tedu.cart.controller;

import cn.tedu.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;
    //查询我的购物车
    @RequestMapping("/query")
    public List<Cart> queryMyCarts(String userId){
        return cartService.queryMyCarts(userId);
    }
    //新增购物车cart
    @RequestMapping("/save")
    public SysResult addCart(Cart cart){
        try{
            cartService.addCart(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "新增购物车失败",
                    null);
        }
    }
    //更新购物车
    @RequestMapping("/update")
    public SysResult updateCartNum(Cart cart){
        try{
            cartService.updateCartNum(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"更新失败",null);
        }
    }
    //删除购物车
    @RequestMapping("/delete")
    public SysResult deleteCart(Cart cart){
        try{
            //delete from t_cart where user_id and product_id
            cartService.deleteCart(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"删除失败",null);
        }
    }
}
