package org.jmlspecs.openjml;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the scanner tokens that represent JML syntax.
 * 
 * @author David Cok
 */

// These are represented as enums. I'm not sure that is the best design since,
// although Enums are nice, they are not extensible. That is how Token is
// implemented, and we could not extend that.
// TODO - we should think about how to re-design JmlToken so that it is extensible.
public enum JmlToken {
    STARTJMLCOMMENT("<JMLSTART>"),
    ENDJMLCOMMENT("<JMLEND>"),
    
    // These are statement types
    ASSUME("assume"),  // Keep this one first of the method statement tokens
    ASSERT("assert"),
    HAVOC("havoc"), // Just used in ESC
    DEBUG("debug"),
    SET("set"),
    DECREASES("decreases"),
    LOOP_INVARIANT("loop_invariant"),
    HENCE_BY("hence_by"),
    REFINING("refining"),
    UNREACHABLE("unreachable"), // Keep this one last of the method statement tokens

    // These are modifiers
    PURE("pure",org.jmlspecs.annotations.Pure.class), // Keep this one the first of the modifiers (see the modifiers Map below)
    CODE_JAVA_MATH("code_java_math"),
    CODE_SAFE_MATH("code_safe_math"),
    EXTRACT("extract",org.jmlspecs.annotations.Extract.class),
    GHOST("ghost",org.jmlspecs.annotations.Ghost.class),
    IMMUTABLE("immutable",org.jmlspecs.annotations.Immutable.class), // FIXME - this is an extension - comment
    INSTANCE("instance",org.jmlspecs.annotations.Instance.class),
    MODEL("model",org.jmlspecs.annotations.Model.class),
    NONNULL("non_null",org.jmlspecs.annotations.NonNull.class),
    NULLABLE("nullable",org.jmlspecs.annotations.Nullable.class),
    NULLABLE_BY_DEFAULT("nullable_by_default",org.jmlspecs.annotations.NullableByDefault.class),
    NON_NULL_BY_DEFAULT("non_null_by_default",org.jmlspecs.annotations.NonNullByDefault.class), // TODO: In some code, but not in JML
    HELPER("helper",org.jmlspecs.annotations.Helper.class),
    UNINITIALIZED("uninitialized",org.jmlspecs.annotations.Uninitialized.class),
    MONITORED("monitored",org.jmlspecs.annotations.Monitored.class),
    PEER("peer",org.jmlspecs.annotations.Peer.class),
    QUERY("query",org.jmlspecs.annotations.Query.class),  // FIXME - this is an extension - comment
    READONLY("readonly",org.jmlspecs.annotations.Readonly.class),
    REP("rep",org.jmlspecs.annotations.Rep.class),
    SECRET("secret",org.jmlspecs.annotations.Secret.class),  // FIXME - this is an extension - comment
    SPEC_BIGINT_MATH("spec_bigint_math"),
    SPEC_JAVA_MATH("spec_java_math"),
    SPEC_SAFE_MATH("spec_safe_math"),
    SPEC_PUBLIC("spec_public",org.jmlspecs.annotations.SpecPublic.class),
    SPEC_PROTECTED("spec_protected",org.jmlspecs.annotations.SpecProtected.class),
    CODE_BIGINT_MATH("code_bigint_math"), // Keep this one the last of the modifiers (see the modifiers Map below)
    
    // These are class/interface clause types
    INVARIANT("invariant"),
    INITIALLY("initially"),
    CONSTRAINT("constraint"),
    AXIOM("axiom"),
    REPRESENTS("represents"),
    JMLDECL("jml declaration"), // not a scannable token
    IN("in"),
    MAPS("maps"),
    INITIALIZER("initializer"),
    STATIC_INITIALIZER("static_initializer"),
    MONITORS_FOR("monitors_for"),
    READABLE("readable"),
    WRITABLE("writable"),
    
    // These are related to specification cases
    ALSO("also"),  // Keep this one first
    NORMAL_BEHAVIOR("normal_behavior"),
    BEHAVIOR("behavior"),
    EXCEPTIONAL_BEHAVIOR("exceptional_behavior"),
    ABRUPT_BEHAVIOR("abrupt_behavior"),
    NORMAL_EXAMPLE("normal_example"),
    EXAMPLE("example"),
    EXCEPTIONAL_EXAMPLE("exceptional_example"),
    MODEL_PROGRAM("model_program"),
    IMPLIES_THAT("implies_that"),
    FOR_EXAMPLE("for_example"),
    CODE("code"),  // Keep this one last
    
    // These are the clause types
    REQUIRES("requires"),   // Keep this one first
    ENSURES("ensures"),
    SIGNALS("signals"),
    SIGNALS_ONLY("signals_only"),
    DIVERGES("diverges"),
    WHEN("when"),
    DURATION("duration"),
    WORKING_SPACE("working_space"),
    FORALL("forall"),
    OLD("old"),
    ASSIGNABLE("assignable"),
    ACCESSIBLE("accessible"),
    MEASURED_BY("measured_by"),
    CALLABLE("callable"),
    CAPTURES("captures"),  // Keep this one last
    
    // These are only in model programs
    CHOOSE("choose"),
    CHOOSE_IF("choose_if"),
    BREAKS("breaks"),
    CONTINUES("continues"),
    OR("or"),
    RETURNS("returns"),
    
    // Other misc
    CONSTRUCTOR("constructor"),
    FIELD("field"),
    METHOD("method"),
    NOWARN("nowarn"),
    REFINES("refines"),
    WEAKLY("weakly"),
    
    // These are various tokens related to JML expressions
    BSEXCEPTION("\\exception"), // This is for internal use only, so it is before \result
    BSRESULT("\\result"), // Keep this one the first of the backslash tokens
    BSEVERYTHING("\\everything"),
    BSLOCKSET("\\lockset"),
    BSINDEX("\\index"),
    BSVALUES("\\values"),
    BSNOTHING("\\nothing"),
    BSSAME("\\same"),
    BSNOTSPECIFIED("\\not_specified"),

    BSDURATION("\\duration"),
    BSELEMTYPE("\\elemtype"),
    BSFRESH("\\fresh"),
    BSINVARIANTFOR("\\invariant_for"),
    BSISINITIALIZED("\\is_initialized"),
    BSLBLANY("\\lbl"),
    BSLBLNEG("\\lblneg"),
    BSLBLPOS("\\lblpos"),
    BSMAX("\\max"),
    BSNONNULLELEMENTS("\\nonnullelements"),
    BSNOTASSIGNED("\\not_assigned"),
    BSNOTMODIFIED("\\not_modified"),
    BSOLD("\\old"),
    BSONLYACCESSED("\\only_accessed"),
    BSONLYASSIGNED("\\only_assigned"),
    BSONLYCALLED("\\only_called"),
    BSONLYCAPTURED("\\only_captured"),
    BSPRE("\\pre"),
    BSREACH("\\reach"),
    BSSPACE("\\space"),
    BSTYPEOF("\\typeof"),
    BSTYPELC("\\type"),
    BSWORKINGSPACE("\\working_space"),

    BSBIGINT_MATH("\\bigint_math"),
    BSJAVAMATH("\\java_math"),
    BSSAFEMATH("\\safe_math"),
    BSNOWARN("\\nowarn"),
    BSNOWARNOP("\\nowarn_op"),
    BSWARN("\\warn"),
    BSWARNOP("\\warn_op"),
    
    BSINTO("\\into"),
    BSSUCHTHAT("\\such_that"),

    BSPEER("\\peer"),
    BSREADONLY("\\readonly"),
    BSREP("\\rep"),
    
    // These are quantifier types (also \max )
    BSEXISTS("\\exists"),
    BSFORALL("\\forall"),
    BSMIN("\\min"),
    BSNUMOF("\\num_of"),
    BSPRODUCT("\\product"),
    BSSUM("\\sum"),
    
    // These are JML type literals
    BSTYPEUC("\\TYPE"),
    BSREAL("\\real"),
    BSBIGINT("\\bigint"), // Keep this one the last of the backslash tokens
    
    // These are JML operators (in expressions)
    EQUIVALENCE("<==>"),
    INEQUIVALENCE("<=!=>"),
    IMPLIES("==>"),
    REVERSE_IMPLIES("<=="),
    SUBTYPE_OF("<:"),
    LOCK_LT("<#"),
    LOCK_LE("<#="),
    
    // Other special character combinations
    DOT_DOT(".."),
    LEFT_ARROW("<-"),
    RIGHT_ARROW("->"),
    INFORMAL_COMMENT("(*...*)"),
    SPEC_GROUP_START("{|"),
    SPEC_GROUP_END("|}"),
    
    ;
    
    /** The canonical form of the token in text */
    private String name;
    
    /** The annotation class associated with this token (for modifiers only), if any */
    //@ nullable
    public Class<?> annotationType = null;

    /** A human readable form of the token, and how it appears in text
     * @return the canonical, printable form of the token (not the internal toString() form)
     */
    public String internedName() { return name; }
    
    
    /** Constructs an Enum token from a string - not available to users
     * @param s The string that will be the canonical form of the token
     */
    JmlToken(String s) { name = s.intern(); }
    
    /** Constructs an Enum instance using an empty string */
    JmlToken() { name = "".intern(); }
    
    /** Constructs an Enum token from a string and annotation type - not available to users.
     * Any modifier token should use this constructor
     * @param s The string that will be the canonical form of the token
     */
    JmlToken(String s, Class<?> annotationType) { 
        this.name = s.intern(); 
        this.annotationType = annotationType;
    }
    
    /** This is a map from string to token for backslash tokens; the input string
     * includes the initial backslash.
     */
    public final static Map<String,JmlToken> backslashTokens = new HashMap<String,JmlToken>();
    
    /** This is a map from string to token for all of the tokens, and includes defined synonyms. */
    public final static Map<String,JmlToken> allTokens = new HashMap<String,JmlToken>();
    
    /** This is a set of all the modifier tokens, defined so that it is quick
     * and easy to test if a token is a modifier.
     */
    public final static EnumSet<JmlToken> modifiers = EnumSet.range(PURE,CODE_BIGINT_MATH);  // BSREADONLY added below
    
    /** This is a set of the modifiers that may be used to characterize a type. */
    // @edu.umd.cs.findbugs.annotations.SuppressWarnings("MS_MUTABLE_ARRAY")
    public final static JmlToken[] typeModifiers = new JmlToken[]{NULLABLE,NONNULL,BSREADONLY};
    
    /** This is a set of all of the tokens that begin method specification clauses,
     * defined so that it is quick and easy to test for a given token.
     */
    public final static EnumSet<JmlToken> methodClauseTokens = EnumSet.range(REQUIRES,CAPTURES);
    
    /** This is a set of all of the tokens that begin JML statements in the body of a method,
     * defined so that it is quick and easy to test for a given token.
     */
    public final static EnumSet<JmlToken> methodStatementTokens = EnumSet.range(ASSUME,UNREACHABLE);
    
    /** This is the set of tokens that can occur at the beginning of a specification case */
    public final static EnumSet<JmlToken> specCaseTokens = EnumSet.range(ALSO,CODE);
    
    static {
        for (JmlToken t: EnumSet.range(BSRESULT,BSBIGINT)) {
            backslashTokens.put(t.internedName(),t);
        }
        for (JmlToken t: JmlToken.values()) {
            allTokens.put(t.internedName(),t);
        }
        allTokens.remove(BSEXCEPTION.internedName());
        modifiers.add(BSREADONLY);
        
        // Synonyms
        
        allTokens.put("modifies".intern(),ASSIGNABLE);
        allTokens.put("modifiable".intern(),ASSIGNABLE);
        allTokens.put("pre".intern(),REQUIRES);
        allTokens.put("post".intern(),ENSURES);
        allTokens.put("exsures".intern(),SIGNALS);
        allTokens.put("behaviour".intern(),BEHAVIOR);
        allTokens.put("exceptional_behaviour".intern(),EXCEPTIONAL_BEHAVIOR);
        allTokens.put("normal_behaviour".intern(),NORMAL_BEHAVIOR);
        allTokens.put("abrupt_behaviour".intern(),ABRUPT_BEHAVIOR);
        allTokens.put("decreasing".intern(),DECREASES);
        allTokens.put("maintaining".intern(),LOOP_INVARIANT);
        allTokens.put("refine".intern(),REFINES);
        
    }
}