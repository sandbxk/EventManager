package bll.interfaces;

public interface IExporter
{
    void toFile(IPrintable print);
    void toPrint(IPrintable print);
}
