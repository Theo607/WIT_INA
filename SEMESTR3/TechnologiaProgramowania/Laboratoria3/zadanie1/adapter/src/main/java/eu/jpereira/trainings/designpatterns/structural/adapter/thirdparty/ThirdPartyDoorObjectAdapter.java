package eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty;

import eu.jpereira.trainings.designpatterns.structural.adapter.model.Door;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor.DoorState;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.ThirdPartyDoor.LockStatus;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.IncorrectDoorCodeException;
import eu.jpereira.trainings.designpatterns.structural.adapter.exceptions.CodeMismatchException;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeCodeForUnlockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotChangeStateOfLockedDoor;
import eu.jpereira.trainings.designpatterns.structural.adapter.thirdparty.exceptions.CannotUnlockDoorException;

public class ThirdPartyDoorObjectAdapter implements Door {

    private ThirdPartyDoor thirdPartyDoor;

    public ThirdPartyDoorObjectAdapter() {
        this.thirdPartyDoor = new ThirdPartyDoor();
    }

    public ThirdPartyDoorObjectAdapter(ThirdPartyDoor thirdPartyDoor) {
        this.thirdPartyDoor = thirdPartyDoor;
    }

    @Override
    public void open(String code) throws IncorrectDoorCodeException {
        try {
            thirdPartyDoor.unlock(code);
            thirdPartyDoor.setState(DoorState.OPEN);
        } catch (CannotUnlockDoorException | CannotChangeStateOfLockedDoor e) {
            throw new IncorrectDoorCodeException();
        }
    }

    @Override
    public void close() {
        try {
            thirdPartyDoor.setState(DoorState.CLOSED);
        } catch (CannotChangeStateOfLockedDoor e) {
        }
    }

    @Override
    public boolean isOpen() {
        return thirdPartyDoor.getState() == DoorState.OPEN;
    }

    @Override
    public void changeCode(String oldCode, String newCode, String newCodeConfirmation)
            throws IncorrectDoorCodeException, CodeMismatchException {

        if (!newCode.equals(newCodeConfirmation)) {
            throw new CodeMismatchException();
        }

        try {
            if (thirdPartyDoor.getLockStatus() == LockStatus.LOCKED) {
                thirdPartyDoor.unlock(oldCode);
            }
            thirdPartyDoor.setNewLockCode(newCode);
            thirdPartyDoor.lock();
        } catch (CannotUnlockDoorException e) {
            throw new IncorrectDoorCodeException();
        } catch (CannotChangeCodeForUnlockedDoor e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean testCode(String code) {
        try {
            LockStatus originalStatus = thirdPartyDoor.getLockStatus();
            if (originalStatus == LockStatus.UNLOCKED) {
                thirdPartyDoor.lock();
            }
            thirdPartyDoor.unlock(code);
            return true;
        } catch (CannotUnlockDoorException e) {
            return false;
        }
    }
}

