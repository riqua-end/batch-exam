package com.example.batchexam.batch;
public class SimpleTasklet<I,O> implements Tasklet {

    private final ItemReader<I> itemReader;
    private final ItemProcessor<I,O> itemProcessor;
    private final ItemWriter<O> itemWriter;

    public SimpleTasklet(ItemReader<I> itemReader, ItemProcessor<I, O> itemProcessor, ItemWriter<O> itemWriter) {
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Override
    public void execute() {
        while (true) {
            // 1. 유저를 조회한다. (read)
            final I read = itemReader.read();
            if (read == null) break;

            // 2. 휴면계정 대상을 추출 및 변환한다. (process)
            final O process = itemProcessor.process(read);
            if (process == null) continue;

            // 3. 휴면계정으로 상태를 변경한다. (write)
            itemWriter.write(process);
        }
    }
}
