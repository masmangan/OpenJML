package org.jmlspecs.openjml.ext;

import org.jmlspecs.openjml.JmlToken;

import com.sun.tools.javac.parser.ExpressionExtension;
import com.sun.tools.javac.parser.JmlParser;
import com.sun.tools.javac.parser.JmlScanner;
import com.sun.tools.javac.parser.Token;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.util.List;

/** This class handles expression extensions that take an argument list of JCExpressions.
 * Even if there are constraints on the number of arguments, it
 * is more robust to accept all of them and then issue an error in the typechecker
 * if the number of arguments is wrong.
 * 
 * @author David Cok
 *
 */// TODO: This extension is inappropriately named at present.  However, I expect that this 
// extension will be broken into individual extensions when type checking and
// RAC and ESC translation are added.
public class Elemtype extends ExpressionExtension {

    public Elemtype() {}
    
    static public JmlToken[] tokens() { return new JmlToken[]{JmlToken.BSELEMTYPE,JmlToken.BSTYPEOF,
            JmlToken.BSOLD,JmlToken.BSPRE,JmlToken.BSNOWARN, JmlToken.BSNOWARNOP,
            JmlToken.BSWARN, JmlToken.BSWARNOP,
            JmlToken.BSBIGINT_MATH, JmlToken.BSJAVAMATH, JmlToken.BSSAFEMATH}; }
    
    public JCExpression parse(JmlParser parser, List<JCExpression> typeArgs) {
        JmlToken jt = scanner.jmlToken();
        int p = scanner.pos();
        scanner.nextToken();
        if (scanner.token() != Token.LPAREN) {
            return parser.syntaxError(p, null, "jml.args.required", jt.internedName());
        } else if (typeArgs != null && !typeArgs.isEmpty()) {
            return parser.syntaxError(p, null, "jml.no.typeargs.allowed", jt.internedName());
        } else {
            int pp = scanner.pos();
            List<JCExpression> args = parser.arguments();
            JCExpression t = toP(parser.maker().at(pp).JmlMethodInvocation(jt, args));
            return parser.primarySuffix(t, typeArgs);
        }

    }
    
//    public Type typecheck(JmlAttr attr, JCExpression expr, Env<AttrContext> localEnv) {
//        JmlMethodInvocation tree = (JmlMethodInvocation)expr;
//        JmlToken token = tree.token;
//        
//        // Expect one argument of any array type, result type is \TYPE
//        // The argument expression may contain JML constructs
//        attr.attribArgs(tree.args, localEnv);
//        attr.attribTypes(tree.typeargs, localEnv);
//        int n = tree.args.size();
//        if (n != 1) {
//            Log.instance(context).error(tree.pos(),"jml.wrong.number.args",token.internedName(),1,n);
//        }
//        Type t = attr.syms.errType;
//        if (n > 0) {
//            //attribTree(tree.args.get(0), localEnv, pkind, syms.classType); // FIXME - THIS DOES not work either
//            if (tree.args.get(0).type == attr.TYPE) {
//                t = attr.TYPE;
//            } else if (tree.args.get(0).type.tsym == attr.syms.classType.tsym) {  // FIXME - syms.classType is a parameterized type which is not equal to the argumet (particularly coming from \\typeof - using tsym works, but we ought to figure this out
//                t = attr.syms.classType;
//            } else {
//                error(tree.args.get(0).pos(),"jml.elemtype.expects.classtype",tree.args.get(0).type.toString());
//                t = attr.TYPE;
//            }
//        }
//        // FIXME - need to check that argument is an array type - see comment above
//        return check(tree, t, VAL, pkind, pt);
//    }
}