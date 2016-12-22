package com.andaily.springoauth.web;

import com.andaily.springoauth.service.OauthService;
import com.andaily.springoauth.service.dto.CustomerDto;
import com.andaily.springoauth.service.dto.PayVO;
import com.andaily.springoauth.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Handle visit Oauth resources, must be have 'access_token'
 *
 * @author Shengzhao Li
 */
@Controller
public class ResourcesController {

    @Autowired
    private OauthService oauthService;

    @Value("#{properties['payUrl']}")
    private String payUrl;

    /*
     * Visit unity role for get user information from oauth server
     */
    @RequestMapping("unity_user_info")
    public String unityUserInfo(String access_token, Model model) {
        UserDto userDto = oauthService.loadUnityUserDto(access_token);

        if (userDto.error()) {
            // error
            model.addAttribute("message", userDto.getErrorDescription());
            model.addAttribute("error", userDto.getError());
            return "redirect:oauth_error";
        } else {
            model.addAttribute("userDto", userDto);
            return "resources/unity_user_info";
        }

    }

    /*
     * Visit unity role for get user information from oauth server
     */
    @RequestMapping("pay")
    public String pay(String access_token, Model model, HttpServletRequest request) {
        String result = oauthService.pay(access_token, request);

        model.addAttribute("result", result);
        return "resources/unity_user_info";

    }

    /*
  * Visit unity role for get user information from oauth server
  */
    @RequestMapping("realPay")
    public String realPay(PayVO payVO, Model model, HttpServletRequest request) {
        String result = oauthService.realPay(payVO, request);

        model.addAttribute("result", result);
        return "resources/unity_user_info";

    }

    /*
  * Visit unity role for get user information from oauth server
  */
    @RequestMapping("to_pay")
    public String toPay(String access_token, Model model, HttpServletRequest request) {

        model.addAttribute("accessToken", access_token);
        model.addAttribute("payUrl", payUrl);

        return "resources/pay";

    }


    /*
    * Visit unity role for get user information from oauth server
    */
    @RequestMapping("customerinfo")
    public String customerinfo(String access_token, Model model, HttpServletRequest request) {
        CustomerDto result = oauthService.readCustomerInfo(access_token, request);

        model.addAttribute("result", result.getOriginalText());
        return "resources/unity_user_info";

    }

}
