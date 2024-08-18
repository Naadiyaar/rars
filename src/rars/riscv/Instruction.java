package rars.riscv;

import rars.AssemblyException;
import rars.assembler.TokenList;
import rars.assembler.Tokenizer;

import java.util.StringTokenizer;

/**
 * Base class to represent member of RISCV instruction set.
 *
 * @author Pete Sanderson and Ken Vollmar
 * @version August 2003
 */

public abstract class Instruction {
    /**
     * Length in bytes of a machine instruction. Currently just 4 because other
     * instruction sizes defined in the specification are nor supported.
     */
    public static final int INSTRUCTION_LENGTH = 4;
    public static final int INSTRUCTION_LENGTH_BITS = 32;
    /**
     * Characters used in instruction mask to indicate bit positions
     * for 'f'irst, 's'econd, 't'hird, 'q'uad, and 'p'enta operands .
     **/
    public static char[] operandMask = {'f', 's', 't', 'q', 'p'};
    /**
     * The instruction name.
     **/
    protected String mnemonic;
    /**
     * Example usage of this instruction.  Is provided as subclass constructor argument.
     **/
    protected String exampleFormat;
    /**
     * Description of instruction for display to user
     **/
    protected String description;
    /**
     * List of tokens generated by tokenizing example usage (see <tt>exampleFormat</tt>).
     **/
    protected TokenList tokenList;


    /**
     * Get operation mnemonic
     *
     * @return operation mnemonic (e.g. addi, sw)
     */
    public String getName() {
        return mnemonic;
    }

    /**
     * Get string descriptor of instruction's format.  This is an example MIPS
     * assembler instruction usage which contains the operator and all operands.
     * Operands are separated by commas, an operand that is the standard name or
     * machine name for a register represents a register, and an integer operand
     * represents an immediate value or address.  Here are two examples:
     * "or x1,x2,x3" and "sw x1,100(x2)"
     *
     * @return String representing example instruction format.
     */
    public String getExampleFormat() {
        return exampleFormat;
    }

    /**
     * Get string describing the instruction.  This is not used internally by
     * RARS, but is for display to the user.
     *
     * @return String describing the instruction.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get TokenList corresponding to correct instruction syntax.
     * For example, the instruction with format "sw x1, 100(x2)" yields token list
     * operator:register_number:integer:left_paren:register_number:right_parent
     *
     * @return TokenList object representing correct instruction usage.
     */

    public TokenList getTokenList() {
        return tokenList;
    }


    /**
     * Get length in bytes that this instruction requires in its binary form.
     * Default is 4 (holds for all basic instructions), but can be overridden
     * in subclass.
     *
     * @return int length in bytes of corresponding binary instruction(s).
     */

    public int getInstructionLength() {
        return INSTRUCTION_LENGTH;
    }

    /**
     * Used by subclass constructors to extract operator mnemonic from the
     * instruction example.
     **/

    protected String extractOperator(String example) {
        StringTokenizer st = new StringTokenizer(example, " ,\t");
        return st.nextToken();
    }

    /**
     * Used to build a token list from the example instruction
     * provided as constructor argument.  Parser uses this for syntax checking.
     **/
    protected void createExampleTokenList() {
        try {
            tokenList = ((new Tokenizer()).tokenizeExampleInstruction(exampleFormat));
        } catch (AssemblyException pe) {
            System.out.println("CONFIGURATION ERROR: Instruction example \"" + exampleFormat + "\" contains invalid token(s).");
        }
    }
}
