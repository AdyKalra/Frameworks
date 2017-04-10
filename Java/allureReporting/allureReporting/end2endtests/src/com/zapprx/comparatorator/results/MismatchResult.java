package com.zapprx.comparatorator.results;

public class MismatchResult extends AbstractComparisionResult {
    public static class MismatchValue {
        private final String field;
        private final String expecting;
        private final String got;

        public MismatchValue(String field, String expecting, String got) {
            super();
            this.field = field;
            this.expecting = expecting;
            this.got = got;
        }

        public String getField() {
            return field;
        }

        public String getExpecting() {
            return expecting;
        }

        public String getGot() {
            return got;
        }
    }

    public MismatchResult(String field, String expecting, String got) {
        super(ResultCode.MISMATCH, new MismatchValue(field, expecting, got), null);
    }

    @Override
    public String getMessage() {
        final MismatchValue m = getMismatch();
        return "Mismatch in field '" + m.getField()
                + "', expecting '" + m.getExpecting() + "', got '"
                + m.getGot() + "'.";
    }
}
