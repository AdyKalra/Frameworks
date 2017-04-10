package com.zapprx.comparatorator.results;


public class ErrorResult extends AbstractComparisionResult {
    @SuppressWarnings("serial")
    public static class ComparisonError extends Exception {
        ComparisonError(String m) {
            super(m);
        }
    }

    public ErrorResult(Exception error) {
        super(ResultCode.ERROR, null, error);
    }

    public ErrorResult(String errorString) {
        super(ResultCode.ERROR, null, new ComparisonError(errorString));
    }

    @Override
    public String getMessage() {
        final Exception e = getError();
        return "Error: " + e.getMessage();
    }
}
