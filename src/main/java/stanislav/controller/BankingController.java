package stanislav.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanislav.dto.SendMoneyRequest;
import stanislav.exceptions.AccountNonExistentException;
import stanislav.exceptions.InsufficientFundsException;
import stanislav.service.BankingService;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "${spring.data.rest.base-path}")
public class BankingController {

    private BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    /**
     * Endpoint used to send money from one account to another
     * @param request request that contains needed information for the transfer:
     *      *                from which account
     *      *                to which account
     *      *                amount to transfer
     * @return a response entity to be displayed to the user
     */
    @PostMapping("/sendMoney")
    public ResponseEntity<String> sendMoney(@RequestBody(required = false) SendMoneyRequest request) {
        //validate request manually as otherwise Spring shows a non-informative error message
        //default error message will be displayed if the request contains the wrong data type for the fields
        if(null == request) {
            return new ResponseEntity<>("Request body is empty! Please provide values for 'fromAccount', 'toAccount' and 'amount'.", HttpStatus.BAD_REQUEST);
        }
        if(null == request.getFromAccount() || null == request.getToAccount() || null == request.getAmount()) {
            return new ResponseEntity<>("Missing parameters in request body! Please provide values for 'fromAccount', 'toAccount' and 'amount'.", HttpStatus.BAD_REQUEST);
        }
        if(request.getAmount().scale() > 2) {
            return new ResponseEntity<>("Please enter amount with up to 2 digits to the right of the decimal point!", HttpStatus.BAD_REQUEST);
        }
        if(request.getToAccount().equals(request.getFromAccount())) {
            return new ResponseEntity<>("Values 'fromAccount' and 'toAccount' can't be the same!", HttpStatus.BAD_REQUEST);
        }
        if(request.getAmount().compareTo(new BigDecimal("0.00")) < 1) {
            return new ResponseEntity<>("You can only transfer a positive amount of money with value greater than 0!", HttpStatus.BAD_REQUEST);
        }

        try {
            bankingService.sendMoney(request);
        } catch (InsufficientFundsException | AccountNonExistentException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error! " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    /**
     * Endpoint used to get an account's balance and list of transactions
     * @param accountId path variable indicates which account's information the user wants displayed
     * @return a response entity to be displayed to the user
     */
    @GetMapping("/accountBalance/{accountId}")
    public ResponseEntity<String> getAccountBalanceAndTransactions(@PathVariable int accountId) {
        try {
            return new ResponseEntity<>(bankingService.getAccountBalanceAndTransactions(accountId), HttpStatus.OK);
        } catch (AccountNonExistentException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error! " + e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}