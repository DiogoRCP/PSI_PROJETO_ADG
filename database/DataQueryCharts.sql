-- Reparações por mes/ano
SELECT month(repairdate) as 'Mês', count(*) as 'Reparações' FROM carbuddy.repairs where year(repairdate) = 2021 group by month(repairdate);
-- Agendamentos por mes/ano
SELECT month(currentdate) as 'Mês', count(*) as 'Agendamentos' FROM carbuddy.schedules where year(currentdate) = 2021 group by month(currentdate);
-- Empresas por mes/ano
SELECT month(registrationdate) as 'Mês', count(*) as 'Empresas' FROM carbuddy.companies where year(registrationdate) = 2021 group by month(registrationdate);
-- Utilizadores por mes/ano
SELECT month(registrationdate) as 'Mês', count(*) as 'Utilizadores' FROM carbuddy.user where year(registrationdate) = 2021 group by month(registrationdate);
-- Utilizadores por ano
SELECT modelyear as "Ano", count(*) as Carros FROM carbuddy.cars group by modelyear;

