package com.zhangyongsic.framework.shiro.factory;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;


/**
 * @author fanchao
 */
public class StatelessSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // context.setSessionCreationEnabled(true);
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
