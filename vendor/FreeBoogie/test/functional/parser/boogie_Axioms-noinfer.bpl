const Seven: int;
axiom Seven == 7;

function inc(int) returns (int);
axiom (forall j: int :: inc(j) == j+1);

procedure P()
{
  start:
    assert 4 <= Seven;
    assert Seven < inc(Seven);
    assert inc(5) + inc(inc(2)) == Seven + 3;
    return;
}

procedure Q()
{
  start:
    assert inc(5) + inc(inc(2)) == Seven;  // error
    return;
}

