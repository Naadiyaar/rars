package rars.riscv.instructions;

import rars.ProgramStatement;
import rars.riscv.hardware.RegisterFile;

public class BGE extends Branch {
    public BGE() {
        super("bge t1,t2,label", "Branch if greater than or equal: Branch to statement at label's address if t1 is greater than or equal to t2", "101");
    }

    public boolean willBranch(ProgramStatement statement) {
        int[] operands = statement.getOperands();
        return RegisterFile.getValueLong(operands[0]) >= RegisterFile.getValueLong(operands[1]);
    }
}