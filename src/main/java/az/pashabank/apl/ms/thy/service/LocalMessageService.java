/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package az.pashabank.apl.ms.thy.service;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

@Service("localMessageService")
public class LocalMessageService {

    @Resource(name = "messageSource")
    private ResourceBundleMessageSource messageSource;

    public String get(final String message, Locale locale) {
        return messageSource.getMessage(message, null, locale);
    }

    public String get(final String message, String[] variables, Locale locale) {
        return messageSource.getMessage(message, variables, locale);
    }

}
