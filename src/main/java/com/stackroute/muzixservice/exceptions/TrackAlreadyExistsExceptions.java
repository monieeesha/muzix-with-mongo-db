package com.stackroute.muzixservice.exceptions;

public class TrackAlreadyExistsExceptions extends Exception {

        private String message;

        public TrackAlreadyExistsExceptions(String message)
        {
            super(message);
            this.message=message;
        }
}
