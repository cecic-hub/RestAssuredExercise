package enums;

public class PetStatusEnum {
    public enum PetStatus{
        available("available"),
        pending("pending"),
        sold("sold"),
        ;

        private final String status;

        PetStatus(String status) {
            this.status = status;
        }

        public String getPetStatus(){
            return status;
        }
    }
}
