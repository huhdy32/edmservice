package com.server.edm;

import com.server.edm.service.EdmService;
import com.server.edm.service.ServiceManager;
import com.server.edm.service.dto.ServiceStatus;
import com.server.edm.service.factory.FactoryManager;
import com.server.edm.service.factory.ServiceFactory;
import com.server.edm.ui.InputUI;
import com.server.edm.ui.OutputUI;

import java.util.List;
import java.util.function.Consumer;

public class EdmServiceController implements Runnable {
    private final ServiceManager serviceManager;
    private final FactoryManager factoryManager;
    private final Thread serverThread;
    private final InputUI inputUI;
    private final OutputUI outputUI;

    public EdmServiceController(ServiceManager serviceManager, FactoryManager factoryManager, Thread thread, InputUI inputUI, OutputUI outputUI) {
        this.serviceManager = serviceManager;
        this.factoryManager = factoryManager;
        this.serverThread = thread;
        this.inputUI = inputUI;
        this.outputUI = outputUI;
    }

    @Override
    public void run() {
        this.serverThread.start();
        startController();
    }

    private void startController() {
        final List<Routine> menus = List.of(new Routine("모든 서비스 보기", this::showAllServices), new Routine("새로 서비스 생성하기", this::createService),new Routine("서비스 실행하기", this::executeService));
        runRoutine(menus, index -> menus.get(index).run());
    }

    private void runRoutine(final List<?> menus, final Consumer<Integer> consumer) {
        while (true) {
            final int input = Selecter.getSelect(menus, inputUI, outputUI);
            if (input == -1) {
                outputUI.printInfo("프로그램을 종료합니다\n");
                break;
            }
            consumer.accept(input);
            outputUI.printInfo("\n");
        }
        serverThread.interrupt();
    }

    private void createService() {
        final List<ServiceFactory> factories = factoryManager.getAllFactories();
        runRoutine(factories, index -> {
            final ServiceFactory serviceFactory = factories.get(index);
            outputUI.printInfo("서비스 이름을 정해주세요\n");
            final String serviceName = inputUI.readLine();
            serviceManager.addService(serviceFactory.create(serviceName));
        });
    }

    private void showAllServices() {
        final StringBuilder info = new StringBuilder();
        final List<ServiceStatus> statuses = serviceManager.getServicesInfo();
        if (statuses.isEmpty()) {
            outputUI.printInfo("현재 만들어진 서비스가 존재하지 않음\n");
            return;
        }
        Selecter.showMenu(statuses, outputUI);
        outputUI.printInfo(info.toString());
    }

    private void executeService() {
        final List<EdmService> services = serviceManager.getAllServices();
        runRoutine(services, index -> services.get(index).doService());
    }

    private static class Routine {
        @Override
        public String toString() {
            return "Routine : " + routineName;
        }

        private final String routineName;
        private final Runnable routine;

        public Routine(String routineName, Runnable runnable) {
            this.routineName = routineName;
            this.routine = runnable;
        }

        public void run() {
            this.routine.run();
        }
    }

    private static class Selecter {
        private static final int EXIT_SIGNAL = -1;

        public static <T> int getSelect(List<T> menus, InputUI inputUI, OutputUI outputUI) {
            showMenu(menus, outputUI);
            final int index = getInputNumber(inputUI, outputUI, menus);
            if (index == EXIT_SIGNAL) {
                return -1;
            }
            return index;
        }

        private static void showMenu(final List<?> menus, OutputUI outputUI) {
            StringBuilder info = new StringBuilder();
            info.append("[-1] 나가기\n");
            for (int i = 0; i < menus.size(); i++) {
                info.append("[" + i + "]" + " " + menus.get(i) + "\n");
            }
            outputUI.printInfo(info.toString());
        }

        private static int getInputNumber(final InputUI inputUI, final OutputUI outputUI, final List<?> menus) {
            while (true) {
                try {
                    outputUI.printInfo("입력 >> ");
                    final int response = Integer.parseInt(inputUI.readLine());
                    if (response >= menus.size() || response < EXIT_SIGNAL) {
                        throw new IllegalArgumentException("범위에서 벗어남\n");
                    }
                    return response;
                } catch (NumberFormatException e) {
                    outputUI.printInfo("숫자를 입력해라\n");
                } catch (IllegalArgumentException e) {
                    outputUI.printInfo("정해진 범위에서 입력해라\n");
                }
            }
        }
    }
}
