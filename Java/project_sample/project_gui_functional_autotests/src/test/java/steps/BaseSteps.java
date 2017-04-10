package steps;

import com.project.reporting.AllureAttachments;
import com.project.util.DriverUtils;
import com.project.util.StepContainer;
import com.project.util.WebDriverWrap;
import configuration.SpringConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by User on 15.02.2017.
 */
@ContextConfiguration(classes = SpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BaseSteps {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected WebDriverWrap driver;
    @Autowired
    protected DriverUtils driverUtils;
    @Autowired
    protected AllureAttachments allureAttachments;
    @Autowired
    protected StepContainer stepContainer;
}
