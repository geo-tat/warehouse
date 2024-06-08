1) Старт миникуба

* minikube start

2) Запуск манифестов

* kubectl apply -f ./deployment/deployment.yaml
* kubectl apply -f ./deployment/service.yaml
* kubectl apply -f ./deployment/ingress.yaml

3) Команды для справки

* kubectl get pods
* kubectl logs warehouse
* kubectl describe pod warehouse
* kubectl get services
* kubectl describe service warehouse-service
* kubectl get ingress
* kubectl describe ingress warehouse-ingress

4) Проброс порта (для работы с микросервисом)

* kubectl port-forward <pod_name> 8001:8080

5)
    * minikube stop

