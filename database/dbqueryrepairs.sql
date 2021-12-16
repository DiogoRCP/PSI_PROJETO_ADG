Use carbuddy;
SELECT count(*) as 'Repairs', companies.companyname
FROM repairs
inner join contributors on repairs.contributorId = contributors.id
inner join companies on contributors.companyId = companies.id
group by companies.companyname;
