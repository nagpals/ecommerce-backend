package com.barclays.ecommerce.service;

import com.barclays.ecommerce.model.User;
import com.barclays.ecommerce.repository.UserRepository;
import com.barclays.ecommerce.util.ECommerceConstants;
import com.barclays.ecommerce.util.ErrorCodes;
import com.barclays.ecommerce.util.GenericLogger;
import com.barclays.ecommerce.view.ECommerceResponse;
import com.barclays.ecommerce.view.GetUserResponse;
import com.barclays.ecommerce.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Class helps to create, get user info
 *
 * @author subashchandar s
 */
@Component
public class UserManagementService {

    private static final Logger logger = LogManager.getLogger(UserManagementService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Method helps to create the user
     *
     * @param uuid
     * @param userView
     * @return
     */
    public ECommerceResponse createUser(String uuid, UserView userView) {
        ECommerceResponse eCommerceResponse = new ECommerceResponse();
        try {
            Optional<User> userModelOptional = userRepository.findById(userView.getUserId());
            if (userModelOptional.isPresent()) {
                // returning error response if already record exits
                eCommerceResponse.setResponseMessage(ErrorCodes.E_COMM_01_DESC);
                eCommerceResponse.setSuccess(false);
                GenericLogger.logResponse(logger, uuid, "FAILURE", ErrorCodes.E_COMM_01_DESC);
            } else {
                User user = new User();
                user.setUserId(userView.getUserId());
                user.setName(userView.getName());
                user.setEmailId(userView.getEmailId());
                user.setPhoneNo(userView.getPhoneNo());
                userRepository.save(user);
                eCommerceResponse.setResponseMessage(ECommerceConstants.CREATE_RECORD_SUCCESS);
                eCommerceResponse.setSuccess(true);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", ECommerceConstants.CREATE_RECORD_SUCCESS);
            }
        } catch (Exception e) {
            eCommerceResponse.setSuccess(false);
            eCommerceResponse.setResponseMessage(ECommerceConstants.CREATE_RECORD_FAILURE);
            // Logger error response
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.CREATE_RECORD_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return eCommerceResponse;
    }

    /**
     * Method helps to create the user
     *
     * @param uuid
     * @param userId
     * @return
     */
    public GetUserResponse getUser(String uuid, String userId) {
        GetUserResponse getUserResponse = new GetUserResponse();
        try {
            Optional<User> userModelOptional = userRepository.findById(userId);
            if (userModelOptional.isPresent()) {
                User user = userModelOptional.get();
                getUserResponse.setUserId(user.getUserId());
                getUserResponse.setName(user.getName());
                getUserResponse.setEmailId(user.getEmailId());
                getUserResponse.setPhoneNo(user.getPhoneNo());
                getUserResponse.setSuccess(true);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", getUserResponse);
            } else {
                // returning error response if record is not exits
                getUserResponse.setResponseMessage(ErrorCodes.E_COMM_02_DESC);
                getUserResponse.setSuccess(false);
            }

        } catch (Exception e) {
            getUserResponse.setSuccess(false);
            getUserResponse.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            // Logger error response
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return getUserResponse;
    }

    /**
     * getLoggedIn
     *
     * @param uuid
     * @param logInId
     * @return
     */
    public GetUserResponse getLoggedIn(String uuid, String logInId) {
        GetUserResponse getUserResponse = new GetUserResponse();
        try {
            User user = userRepository.findUserByEmailId(logInId);
            if (user == null) {
                user = userRepository.findUserByPhoneNo(logInId);
            }
            if (user != null) {
                getUserResponse.setUserId(user.getUserId());
                getUserResponse.setName(user.getName());
                getUserResponse.setEmailId(user.getEmailId());
                getUserResponse.setPhoneNo(user.getPhoneNo());
                getUserResponse.setSuccess(true);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", getUserResponse);
            } else {
                // returning error response if record is not exits
                getUserResponse.setResponseMessage(ErrorCodes.E_COMM_02_DESC);
                getUserResponse.setSuccess(false);
            }
        } catch (Exception e) {
            getUserResponse.setSuccess(false);
            getUserResponse.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            // Logger error response
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return getUserResponse;
    }
}
