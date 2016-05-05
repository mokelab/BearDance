package net.exkazuu.mimicdance;

import android.os.AsyncTask;

import net.exkazuu.mimicdance.interpreter.Interpreter;
import net.exkazuu.mimicdance.program.UnrolledProgram;

/**
 * Command Executor
 */
public class CommandExecutor extends AsyncTask<Void, Void, Void> {

    private static final int SLEEP_TIME = 300;

    private final Interpreter coccoExecutor;
    private final Interpreter altCoccoExecutor;

    public CommandExecutor(UnrolledProgram coccoProgram, UnrolledProgram altCoccoProgram,
                           CharacterImageViewSet coccoViewSet, CharacterImageViewSet altCoccoViewSet) {
        this.coccoExecutor = Interpreter.createForCocco(coccoProgram, coccoViewSet);
        this.altCoccoExecutor = Interpreter.createForCocco(altCoccoProgram, altCoccoViewSet);
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (!(coccoExecutor.finished() && altCoccoExecutor.finished())) {
            // アニメーションは2コマから構成
            for (int j = 0; j < 2; j++) {
                if (isCancelled()) {
                    return null;
                }
                publishProgress();
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        this.coccoExecutor.run();
        this.altCoccoExecutor.run();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (!isCancelled()) {
            this.coccoExecutor.run();
            this.altCoccoExecutor.run();
        }
    }
}
