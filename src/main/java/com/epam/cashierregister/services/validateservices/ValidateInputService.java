package com.epam.cashierregister.services.validateservices;

import com.epam.cashierregister.services.ValidateService;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * An abstract class used to validate input data
 * use <code>ValidateService</code> for validation
 * @see ValidateService
 */
public abstract class ValidateInputService {
    protected static Logger LOG = LogManager.getLogger(ValidateInputService.class);
    protected HttpServletRequest request;
    protected ValidateService validateField;


    /**
     * @param request that contains parameters for validation
     */
    protected ValidateInputService(HttpServletRequest request) {
        this.validateField = new ValidateService();
        this.request = request;
    }


    /**
     * @throws InvalidInputException with error message if any of fields did not pass the inspection
     * @throws DatabaseException if something go wrong with database
     */
    public abstract void validate() throws InvalidInputException, DatabaseException;


}
